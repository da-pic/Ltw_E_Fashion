package controllers;

import java.io.IOException;

import dao.DashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/admin/dashboard"})
public class AdminDashboard extends HttpServlet {
    private DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("totalProducts", dashboardDAO.getTotalActiveProducts());
        request.setAttribute("totalUsers", dashboardDAO.getTotalUsers());
        request.setAttribute("pendingOrders", dashboardDAO.getPendingOrders());
        request.setAttribute("totalRevenue", dashboardDAO.getTotalRevenue());
        
        java.util.Map<String, Integer> statusData = dashboardDAO.getOrderStatusData();
        
        request.setAttribute("countDelivered", statusData.getOrDefault("delivered", 0));
        request.setAttribute("countShipping", statusData.getOrDefault("shipping", 0));
        request.setAttribute("countPending", statusData.getOrDefault("pending", 0));
        request.setAttribute("countCanceled", statusData.getOrDefault("canceled", 0));

        java.util.Map<String, Integer> categoryData = dashboardDAO.getProductsByCategory();
        StringBuilder catLabels = new StringBuilder();
        StringBuilder catValues = new StringBuilder();

        for (java.util.Map.Entry<String, Integer> entry : categoryData.entrySet()) {
            catLabels.append("'").append(entry.getKey()).append("',");
            catValues.append(entry.getValue()).append(",");
        }

        if (catLabels.length() > 0) {
            catLabels.setLength(catLabels.length() - 1);
            catValues.setLength(catValues.length() - 1);
        }

        request.setAttribute("catLabels", catLabels.toString());
        request.setAttribute("catValues", catValues.toString());
        
        request.getRequestDispatcher("/WEB-INF/AdminViews/dashboard.jsp").forward(request, response);

        request.getRequestDispatcher("/WEB-INF/AdminViews/dashboard.jsp").forward(request, response);
    }
}