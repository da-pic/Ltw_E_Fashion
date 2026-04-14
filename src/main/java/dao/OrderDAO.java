package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderDAO {

    public String generateOrderId() {
        return "ord-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void insertOrder(Connection conn, String orderId, String userId,
                            int total, int shippingFee, String paymentMethod,
                            String couponId, String status, int addressId) throws Exception {

        String sql = "INSERT INTO orders (id, user_id, total_price, shipping_fee, payment_method, coupon_id, status, address_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ps.setString(2, userId);
            ps.setInt(3, total);
            ps.setInt(4, shippingFee);
            ps.setString(5, paymentMethod);

            if (couponId == null || couponId.isEmpty()) {
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

        if (items == null || items.isEmpty()) {
            throw new Exception("Cart rỗng!");
        }

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

    public void insertOrderAddressLog(Connection conn, String orderId, Address a) throws Exception {

        if (a == null) {
            throw new Exception("Address không tồn tại");
        }

        String fullAddress = a.getStreet() + ", " + a.getDistrict() + ", " + a.getCity();

        String sql = "INSERT INTO order_address_log (order_id, full_address, phone_number) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            ps.setString(2, fullAddress);
            ps.setString(3, a.getPhoneNumber());
            ps.executeUpdate();
        }
    }
    public void updateOrderStatus(String orderId, String status) throws Exception {

    String sql = "UPDATE orders SET status = ? WHERE id = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, status);
        ps.setString(2, orderId);

        ps.executeUpdate();
    }
}
    public List<Order> getAllOrders() throws Exception {
    List<Order> list = new ArrayList<>();

    String sql = "SELECT * FROM orders ORDER BY id DESC";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Order o = new Order();
            o.setId(rs.getString("id"));
            o.setUserId(rs.getString("user_id"));
            o.setTotalPrice(rs.getInt("total_price"));
            o.setShippingFee(rs.getInt("shipping_fee"));
            o.setPaymentMethod(rs.getString("payment_method"));
            o.setCouponId(rs.getString("coupon_id"));
            o.setStatus(rs.getString("status"));
            o.setAddressId(rs.getInt("address_id"));

            list.add(o);
        }
    }

    return list;
}
}