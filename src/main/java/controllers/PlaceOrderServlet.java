package controllers;

import model.User;
import service.OrderService;
import service.OrderService.PlaceOrderResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/seller/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        String addressIdRaw = request.getParameter("addressId");
        String paymentMethod = request.getParameter("paymentMethod");
        String couponId      = request.getParameter("couponId");

        if (addressIdRaw == null || addressIdRaw.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn địa chỉ giao hàng.");
            request.getRequestDispatcher("/seller/checkout").forward(request, response);
            return;
        }

        try {
            int addressId = Integer.parseInt(addressIdRaw);

            PlaceOrderResult result = orderService.placeOrder(
                    user.getId(), addressId, paymentMethod, couponId);

            request.setAttribute("orderId",    result.getOrderId());
            request.setAttribute("finalTotal", result.getFinalTotal());
            request.getRequestDispatcher("/WEB-INF/seller/order-success.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/seller/checkout").forward(request, response);
        }
    }
}