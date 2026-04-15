package service;

import dao.*;
import model.*;

import java.sql.Connection;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final CouponDAO couponDAO = new CouponDAO();
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAO();

    /**
     * Lấy toàn bộ danh sách đơn hàng.
     */
    public List<Order> getAllOrders() throws Exception {
        return orderDAO.getAllOrders();
    }

    /**
     * Cập nhật trạng thái đơn hàng (ship / cancel).
     */
    public void updateOrderStatus(String orderId, String action) throws Exception {
        if ("ship".equals(action)) {
            orderDAO.updateOrderStatus(orderId, "shipping");
        } else if ("cancel".equals(action)) {
            orderDAO.updateOrderStatus(orderId, "canceled");
        } else {
            throw new Exception("Hành động không hợp lệ: " + action);
        }
    }

    /**
     * Đặt hàng: validate → tính tiền → transaction → commit.
     * Trả về PlaceOrderResult chứa orderId và finalTotal để Controller forward.
     */
    public PlaceOrderResult placeOrder(String userId, int addressId,
                                       String paymentMethod, String couponId) throws Exception {

        // --- Validate payment method ---
        if (paymentMethod == null
                || (!paymentMethod.equals("cash") && !paymentMethod.equals("transfer"))) {
            throw new Exception("Phương thức thanh toán không hợp lệ.");
        }

        // --- Lấy giỏ hàng ---
        String cartId = cartDAO.getCartIdByUserId(userId);
        if (cartId == null) throw new Exception("Không tìm thấy giỏ hàng.");

        List<CartItem> cartItems = cartDAO.getCartItems(cartId);
        if (cartItems == null || cartItems.isEmpty()) throw new Exception("Giỏ hàng đang trống.");

        // --- Validate địa chỉ ---
        Address address = addressDAO.getAddressByIdAndUserId(addressId, userId);
        if (address == null) throw new Exception("Địa chỉ không hợp lệ.");

        // --- Tính subtotal & kiểm tra tồn kho ---
        int subtotal = 0;
        for (CartItem item : cartItems) {
            if (item.getAmount() > item.getStock()) {
                throw new Exception("Sản phẩm " + item.getProductName() + " không đủ tồn kho.");
            }
            subtotal += item.getLineTotal();
        }

        int shippingFee = calculateShippingFee(subtotal);

        // --- Xử lý coupon ---
        Coupon coupon = null;
        int discountAmount = 0;

        if (couponId != null && !couponId.trim().isEmpty()) {
            coupon = couponDAO.getValidCouponOfUser(userId, couponId);
            if (coupon == null) throw new Exception("Mã giảm giá không hợp lệ hoặc đã hết lượt dùng.");
            if (subtotal < coupon.getMinCost())
                throw new Exception("Đơn hàng chưa đủ điều kiện áp dụng mã giảm giá.");

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

        // --- Transaction ---
        Connection conn = null;
        try {
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
            return new PlaceOrderResult(orderId, finalTotal);

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            throw e;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    private int calculateShippingFee(int subtotal) {
        return subtotal >= 1_000_000 ? 0 : 30_000;
    }
    public static class PlaceOrderResult {
        private final String orderId;
        private final int finalTotal;

        public PlaceOrderResult(String orderId, int finalTotal) {
            this.orderId = orderId;
            this.finalTotal = finalTotal;
        }

        public String getOrderId()  { return orderId; }
        public int getFinalTotal()  { return finalTotal; }
    }
}