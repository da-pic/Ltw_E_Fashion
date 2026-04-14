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

import model.Employee;
import dao.EmployeeDAO;
import java.util.List;

/**
 *
 * @author qatri
 */
@WebServlet(name = "AdminEmployee", urlPatterns = {"/admin/employees"})
public class AdminEmployee extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> list = employeeDAO.getAllEmployees();

        request.setAttribute("employeeList", list);

        request.getRequestDispatcher("/WEB-INF/AdminViews/EmployeeManager.jsp")
               .forward(request, response);
    }
    

}
