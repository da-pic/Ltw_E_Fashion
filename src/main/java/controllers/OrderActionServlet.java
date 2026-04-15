package controllers;

import service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/order-action")
public class OrderActionServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderId = request.getParameter("orderId");
        String action  = request.getParameter("action");

        try {
            orderService.updateOrderStatus(orderId, action);
            response.sendRedirect("orders");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("ERROR: " + e.getMessage());
        }
    }
}