package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 

@WebServlet(name = "MyServlet", urlPatterns = {"/"}) 
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if (path == null || path.equals("/") || path.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/CustomerViews/login.jsp").forward(request, response);
        } else {
            request.getServletContext().getNamedDispatcher("default").forward(request, response);
        }
    }
}