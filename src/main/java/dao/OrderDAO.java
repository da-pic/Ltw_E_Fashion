package dao;

import model.Address;
import model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

public class OrderDAO {

    public String generateOrderId() {
        return "ord-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void insertOrder(Connection conn,
                            String orderId,
                            String userId,
                            int totalPrice,
                            int shippingFee,
                            String paymentMethod,
                            String couponId,
                            String status,
                            int addressId) throws Exception {

        String sql = "INSERT INTO orders " +
                "(id, user_id, total_price, shipping_fee, payment_method, coupon_id, status, address_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ps.setString(2, userId);
            ps.setInt(3, totalPrice);
            ps.setInt(4, shippingFee);
            ps.setString(5, paymentMethod);
            if (couponId == null || couponId.trim().isEmpty()) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, couponId);
            }
            ps.setString(7, status);
            ps.setInt(8, addressId);

            ps.executeUpdate();
        }
    }

    public void insertOrderItems(Connection conn, String orderId, List<CartItem> items) throws Exception {
        String sql = "INSERT INTO order_items (order_id, product_variant_id, amount, unit_price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (CartItem item : items) {
                ps.setString(1, orderId);
                ps.setString(2, item.getProductVariantId());
                ps.setInt(3, item.getAmount());
                ps.setInt(4, item.getUnitPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void insertOrderAddressLog(Connection conn, String orderId, Address address) throws Exception {
        String sql = "INSERT INTO order_address_log (order_id, full_address, phone_number) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ps.setString(2, address.getFullAddress());
            ps.setString(3, address.getPhoneNumber());
            ps.executeUpdate();
        }
    }
}