package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import service.UserService;
import java.sql.Date;

@WebServlet(urlPatterns = {"/login", "/register"})
public class AuthController extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/login".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/CustomerViews/login.jsp").forward(request, response);
        } else if ("/register".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/CustomerViews/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();

        if ("/login".equals(path)) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User loggedInUser = userService.login(username, password);

            if (loggedInUser != null) {

                String role = userService.getRole(loggedInUser.getId());

                HttpSession session = request.getSession();
                session.setAttribute("currentUser", loggedInUser);
                session.setAttribute("role", role);

                // 🔥 tránh null
                if (role == null) {
                    role = "customer";
                }

                // 🔥 điều hướng
                if ("customer".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/CustomerHome");
                } else if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }

            } else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("/WEB-INF/CustomerViews/login.jsp").forward(request, response);
            }

        } else if ("/register".equals(path)) {

            User newUser = new User();
            newUser.setName(request.getParameter("name"));
            newUser.setBirthdate(Date.valueOf(request.getParameter("birthdate")));
            newUser.setPhonenumber(request.getParameter("phonenumber"));
            newUser.setGender(request.getParameter("gender"));
            newUser.setUsername(request.getParameter("username"));

            String pass = request.getParameter("password");

            if (userService.register(newUser, pass)) {
                response.sendRedirect(request.getContextPath() + "/login?msg=success");
            } else {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                request.getRequestDispatcher("/WEB-INF/CustomerViews/register.jsp").forward(request, response);
            }
        }
    }
}