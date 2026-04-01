package controllers;

import dao.AddressDAO;
import dao.CartDAO;
import dao.CouponDAO;
import model.Address;
import model.CartItem;
import model.CheckoutSummary;
import model.Coupon;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/seller/checkout")
public class CheckoutServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final CouponDAO couponDAO = new CouponDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            // ✅ FIX: Dùng "currentUser" cho khớp với dashboard.jsp và LoginServlet
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            User user = (User) session.getAttribute("currentUser");
            String userId = user.getId();

            String cartId = cartDAO.getCartIdByUserId(userId);
            if (cartId == null) {
                request.setAttribute("error", "Bạn chưa có giỏ hàng.");
                request.getRequestDispatcher("/WEB-INF/seller/checkout.jsp").forward(request, response);
                return;
            }

            List<CartItem> cartItems = cartDAO.getCartItems(cartId);
            if (cartItems == null || cartItems.isEmpty()) {
                request.setAttribute("error", "Giỏ hàng đang trống.");
                request.getRequestDispatcher("/WEB-INF/seller/checkout.jsp").forward(request, response);
                return;
            }

            List<Address> addresses = addressDAO.getAddressesByUserId(userId);
            List<Coupon> coupons = couponDAO.getAvailableCouponsByUserId(userId);

            int subtotal = 0;
            for (CartItem item : cartItems) {
                subtotal += item.getLineTotal();
            }

            int shippingFee = calculateShippingFee(subtotal);

            CheckoutSummary summary = new CheckoutSummary();
            summary.setSubtotal(subtotal);
            summary.setShippingFee(shippingFee);
            summary.setDiscountAmount(0);
            summary.setFinalTotal(subtotal + shippingFee);

            request.setAttribute("cartItems", cartItems);
            request.setAttribute("addresses", addresses);
            request.setAttribute("coupons", coupons);
            request.setAttribute("summary", summary);

            request.getRequestDispatcher("/WEB-INF/seller/checkout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/seller/checkout.jsp").forward(request, response);
        }
    }

    private int calculateShippingFee(int subtotal) {
        if (subtotal >= 1000000) return 0;
        return 30000;
    }
}