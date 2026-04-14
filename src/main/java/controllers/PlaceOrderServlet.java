package controllers;

import dao.*;
import model.Address;
import model.CartItem;
import model.Coupon;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/seller/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final CouponDAO couponDAO = new CouponDAO();
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = null;

        try {
            HttpSession session = request.getSession(false);
            // ✅ FIX: Dùng "currentUser" cho khớp với dashboard.jsp và LoginServlet
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            User user = (User) session.getAttribute("currentUser");
            String userId = user.getId();

            String addressIdRaw = request.getParameter("addressId");
            String paymentMethod = request.getParameter("paymentMethod");
            String couponId = request.getParameter("couponId");

            if (addressIdRaw == null || addressIdRaw.trim().isEmpty()) {
                throw new Exception("Vui lòng chọn địa chỉ giao hàng.");
            }
            if (paymentMethod == null || (!paymentMethod.equals("cash") && !paymentMethod.equals("transfer"))) {
                throw new Exception("Phương thức thanh toán không hợp lệ.");
            }

            int addressId = Integer.parseInt(addressIdRaw);

            String cartId = cartDAO.getCartIdByUserId(userId);
            if (cartId == null) throw new Exception("Không tìm thấy giỏ hàng.");

            List<CartItem> cartItems = cartDAO.getCartItems(cartId);
            if (cartItems == null || cartItems.isEmpty()) throw new Exception("Giỏ hàng đang trống.");

            Address address = addressDAO.getAddressByIdAndUserId(addressId, userId);
            if (address == null) throw new Exception("Địa chỉ không hợp lệ.");

            int subtotal = 0;
            for (CartItem item : cartItems) {
                if (item.getAmount() > item.getStock()) {
                    throw new Exception("Sản phẩm " + item.getProductName() + " không đủ tồn kho.");
                }
                subtotal += item.getLineTotal();
            }

            int shippingFee = calculateShippingFee(subtotal);

            Coupon coupon = null;
            int discountAmount = 0;

            if (couponId != null && !couponId.trim().isEmpty()) {
                coupon = couponDAO.getValidCouponOfUser(userId, couponId);
                if (coupon == null) throw new Exception("Mã giảm giá không hợp lệ hoặc đã hết lượt dùng.");
                if (subtotal < coupon.getMinCost()) throw new Exception("Đơn hàng chưa đủ điều kiện áp dụng mã giảm giá.");

                if ("product".equals(coupon.getType())) {
                    discountAmount = coupon.getValue() <= 100
                            ? subtotal * coupon.getValue() / 100
                            : coupon.getValue();
                    if (coupon.getMaxDiscount() > 0)
                        discountAmount = Math.min(discountAmount, coupon.getMaxDiscount());
                } else if ("ship".equals(coupon.getType())) {
                    discountAmount = Math.min(coupon.getValue(), shippingFee);
                }
            }

            int finalTotal = Math.max(subtotal + shippingFee - discountAmount, 0);
            String orderId = orderDAO.generateOrderId();

            conn = DBContext.getConnection();
            conn.setAutoCommit(false);

            orderDAO.insertOrder(conn, orderId, userId, finalTotal, shippingFee,
                    paymentMethod, (coupon != null ? coupon.getId() : null), "pending", addressId);
            orderDAO.insertOrderItems(conn, orderId, cartItems);
            orderDAO.insertOrderAddressLog(conn, orderId, address);

            for (CartItem item : cartItems) {
                productVariantDAO.decreaseStock(conn, item.getProductVariantId(), item.getAmount());
            }

            cartDAO.clearCartItems(conn, cartId);

            if (coupon != null) {
                couponDAO.decreaseUserCouponLimit(conn, userId, coupon.getId());
            }

            conn.commit();

            request.setAttribute("orderId", orderId);
            request.setAttribute("finalTotal", finalTotal);
            request.getRequestDispatcher("/WEB-INF/seller/order-success.jsp").forward(request, response);

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/seller/checkout").forward(request, response);

        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private int calculateShippingFee(int subtotal) {
        return subtotal >= 1000000 ? 0 : 30000;
    }
}