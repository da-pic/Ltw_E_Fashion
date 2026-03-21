package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 

@WebServlet(name = "MyServlet", urlPatterns = {"/home"}) 
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("pageTitle", "Hệ thống Quản lý Cửa hàng");
        request.setAttribute("welcomeMessage", "Phần mềm Quản lý Bán hàng");

        String jspPath = "/WEB-INF/UserViews/home.jsp";
        request.getRequestDispatcher(jspPath).forward(request, response);
    }
}