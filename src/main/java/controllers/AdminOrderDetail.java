package controllers;

import java.io.IOException;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

@WebServlet(urlPatterns = {"/admin/orders/detail"})
public class AdminOrderDetail extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            Order order = orderDAO.getOrderById(id);
            if (order != null) {
                request.setAttribute("order", order);
                request.getRequestDispatcher("/WEB-INF/AdminViews/orderDetail.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String orderId = request.getParameter("orderId");
        String newStatus = request.getParameter("status");
        
        if (orderId != null && newStatus != null) {
            orderDAO.updateOrderStatus(orderId, newStatus);
        }
        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }
}