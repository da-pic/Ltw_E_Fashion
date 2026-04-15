package controllers;

import model.Salary;
import model.User;
import service.SalaryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/salary")
public class SalaryServlet extends HttpServlet {

    private final SalaryService salaryService = new SalaryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        String role = currentUser.getRole();

        List<Salary> salaries;

        if ("admin".equals(role)) {
            salaries = salaryService.getAllSalaries();
        } else if ("staff".equals(role)) {
            salaries = salaryService.getSalariesByUserId(currentUser.getId());
        } else {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        request.setAttribute("salaries", salaries);
        request.setAttribute("role",     role);
        request.getRequestDispatcher("/WEB-INF/seller/salary.jsp")
               .forward(request, response);
    }
}