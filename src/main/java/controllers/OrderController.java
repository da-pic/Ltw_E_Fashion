package controllers;

import service.OrderService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "OrderController", urlPatterns = {"/order-success", "/order-history", "/order-cancel"})
public class OrderController extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        if ("/order-success".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/CustomerViews/order-success.jsp").forward(request, response);
            
        } else if ("/order-history".equals(path)) {
            List<Map<String, Object>> orders = orderService.getOrderHistory(currentUser.getId());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/CustomerViews/order-history.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        if ("/order-cancel".equals(path)) {
            String orderId = request.getParameter("orderId");
            
            boolean isCancelled = orderService.cancelOrder(orderId, currentUser.getId());
            
            if (isCancelled) {
                response.sendRedirect(request.getContextPath() + "/order-history?msg=cancel_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/order-history?error=cancel_failed");
            }
        }
    }
}