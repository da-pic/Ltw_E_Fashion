package controllers;

import dao.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/order-action")
public class OrderActionServlet extends HttpServlet {

    private OrderDAO dao = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
        String action = request.getParameter("action");

        try {
            if ("ship".equals(action)) {
                dao.updateOrderStatus(orderId, "shipping");
            } else if ("cancel".equals(action)) {
                dao.updateOrderStatus(orderId, "canceled");
            }

            response.sendRedirect("orders");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("ERROR: " + e.getMessage());
        }
    }
}