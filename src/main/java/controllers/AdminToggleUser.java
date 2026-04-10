package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

@WebServlet(urlPatterns = {"/admin/users/toggle"})
public class AdminToggleUser extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String statusParam = request.getParameter("status");
        
        if (id != null && statusParam != null) {
            boolean newStatus = Boolean.parseBoolean(statusParam);
            userService.updateUserStatus(id, newStatus);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}