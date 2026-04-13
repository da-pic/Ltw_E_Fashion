/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

/**
 *
 * @author Chinh
 */
@WebServlet(name = "UpdateProfile", urlPatterns = {"/UpdateProfile"})
public class UpdateProfile extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String birthdateStr = request.getParameter("birthdate");

        currentUser.setName(name);
        currentUser.setGender(gender);
        java.sql.Date birthdate = java.sql.Date.valueOf(birthdateStr);
        currentUser.setBirthdate(birthdate);

        boolean isUpdated = userService.updateUser(currentUser);

        if (isUpdated) {
            session.setAttribute("currentUser", currentUser);
            response.sendRedirect(request.getContextPath() + "/CustomerProfile?msg=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/CustomerProfile?error=failed");
        }
    }

}