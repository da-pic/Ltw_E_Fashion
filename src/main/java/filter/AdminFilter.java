package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

// Filter này sẽ chặn tất cả các request có URL bắt đầu bằng /admin
@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    private UserService userService = new UserService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("currentUser") != null);

        if (loggedIn) {
            User currentUser = (User) session.getAttribute("currentUser");
            // Do đối tượng User cũ không có role, ta phải query thêm để kiểm tra
            // Cần viết thêm hàm checkRoleAdmin trong UserService (xem Bước 2)
            if (userService.checkRoleAdmin(currentUser.getId())) {
                chain.doFilter(request, response); // Cho phép đi tiếp
            } else {
                // Có đăng nhập nhưng không phải admin -> đá về trang chủ customer
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/CustomerHome");
            }
        } else {
            // Chưa đăng nhập -> đá về trang login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}