package controllers;

import model.Order;
import service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Order> orders = orderService.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/seller/order.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi load đơn hàng!");
        }
    }
}