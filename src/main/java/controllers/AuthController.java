/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;
import java.io.IOException;
import java.sql.Date;

@WebServlet(urlPatterns = {"/login", "/register"})
public class AuthController extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/UserViews/login.jsp").forward(request, response);
        } else if ("/register".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/UserViews/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();

        if ("/login".equals(path)) {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            
            User loggedInUser = userService.login(user, pass);
            if (loggedInUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", loggedInUser);
              //  response.sendRedirect(request.getContextPath() + "/home"); // Chuyển hướng sau khi login thành công
            
              if ("staff".equals(loggedInUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/seller/dashboard.jsp");
              } else if ("customer".equals(loggedInUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/home");
              } else if ("admin".equals(loggedInUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
    }
            }
             
            else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                request.getRequestDispatcher("/WEB-INF/UserViews/login.jsp").forward(request, response);
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
                request.setAttribute("error", "Tên đăng nhập đã tồn tại hoặc lỗi hệ thống!");
                request.getRequestDispatcher("/WEB-INF/UserViews/register.jsp").forward(request, response);
            }
        }
    }
}