package controllers;

import dao.OrderDAO;
import model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    OrderDAO dao = new OrderDAO();

    try {
        List<Order> orders = dao.getAllOrders();

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/seller/order.jsp")
               .forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().println("Lỗi load đơn hàng!");
    }
}
}