package controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;

@WebServlet(urlPatterns = {"/admin/users"})
public class AdminUser extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = userService.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/WEB-INF/AdminViews/userManager.jsp").forward(request, response);
    }
}