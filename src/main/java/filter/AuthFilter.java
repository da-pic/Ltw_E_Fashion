package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import java.io.IOException;

@WebFilter("/*") 
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();

        boolean isPublicAsset = path.startsWith("/static") || path.startsWith("/assets") || 
                               path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png");
        
        boolean isAuthPage = path.equals("/login") || path.equals("/register");

        if (isAuthPage || isPublicAsset) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        } 
        
        if (user.getRole() != null && user.getRole().equalsIgnoreCase("customer")) {
            chain.doFilter(request, response);
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập!");
        }
    }
}