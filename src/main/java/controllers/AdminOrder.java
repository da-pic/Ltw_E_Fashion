package controllers;

import java.io.IOException;
import java.util.List;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

@WebServlet(urlPatterns = {"/admin/orders"})
public class AdminOrder extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderDAO.getAllOrders();
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/WEB-INF/AdminViews/orderManager.jsp").forward(request, response);
    }
}