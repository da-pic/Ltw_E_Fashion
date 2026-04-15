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

            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                request.getRequestDispatcher("/WEB-INF/CustomerViews/login.jsp").forward(request, response);
                return;
            }

            User loggedInUser = userService.login(username, password);

            if (loggedInUser != null) {

                String role = userService.getRole(loggedInUser.getId());

                if (role == null) {
                    role = "customer";
                }

                loggedInUser.setRole(role);

                HttpSession session = request.getSession();
                session.setAttribute("currentUser", loggedInUser);

                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/CustomerHome");
                }

            } else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("/WEB-INF/CustomerViews/login.jsp").forward(request, response);
            }
        }

        else if ("/register".equals(path)) {

            try {
                User newUser = new User();

                newUser.setName(request.getParameter("name"));
                newUser.setBirthdate(Date.valueOf(request.getParameter("birthdate")));
                newUser.setPhonenumber(request.getParameter("phonenumber"));
                newUser.setGender(request.getParameter("gender"));
                newUser.setUsername(request.getParameter("username"));

                String pass = request.getParameter("password");

                if (newUser.getUsername() == null || pass == null ||
                    newUser.getUsername().isEmpty() || pass.isEmpty()) {

                    request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                    request.getRequestDispatcher("/WEB-INF/CustomerViews/register.jsp").forward(request, response);
                    return;
                }

                boolean success = userService.register(newUser, pass);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/login?msg=success");
                } else {
                    request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                    request.getRequestDispatcher("/WEB-INF/CustomerViews/register.jsp").forward(request, response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Dữ liệu không hợp lệ!");
                request.getRequestDispatcher("/WEB-INF/CustomerViews/register.jsp").forward(request, response);
            }
        }
    }
}