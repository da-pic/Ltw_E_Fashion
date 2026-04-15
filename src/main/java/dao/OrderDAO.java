package dao;

import java.sql.*;
import java.util.*;
import util.DatabaseConnection;

public class OrderDAO {

    public boolean placeOrder(String orderId, String userId, int totalPrice, String paymentMethod, int addressId, 
                              String couponId, String[] variantIds, String[] quantities, int[] unitPrices) {
        
        String insertOrder = "INSERT INTO orders (id, user_id, total_price, shipping_fee, payment_method, address_id, coupon_id, order_status, payment_status) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, 'pending', 'unpaid')";
        String insertItem = "INSERT INTO order_items (order_id, product_variant_id, amount, unit_price) VALUES (?, ?, ?, ?)";
        
        String updateCoupon = "UPDATE user_coupon SET `limit` = `limit` - 1 "
                            + "WHERE user_id = ? AND coupon_id = ? AND `limit` > 0";

        String updateStock = "UPDATE product_variants SET stock = stock - ? "
                           + "WHERE id = ? AND stock >= ?";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); 
            
            try (PreparedStatement psOrder = conn.prepareStatement(insertOrder)) {
                psOrder.setString(1, orderId);
                psOrder.setString(2, userId);
                psOrder.setInt(3, totalPrice);
                psOrder.setInt(4, 0); 
                psOrder.setString(5, paymentMethod);
                psOrder.setInt(6, addressId);
                psOrder.setString(7, couponId); 
                psOrder.executeUpdate();
            }
            
            try (PreparedStatement psItem = conn.prepareStatement(insertItem)) {
                for (int i = 0; i < variantIds.length; i++) {
                    psItem.setString(1, orderId);
                    psItem.setString(2, variantIds[i]);
                    psItem.setInt(3, Integer.parseInt(quantities[i]));
                    psItem.setInt(4, unitPrices[i]);
                    psItem.addBatch(); 
                }
                psItem.executeBatch();
            }
            
            try (PreparedStatement psStock = conn.prepareStatement(updateStock)) {
                for (int i = 0; i < variantIds.length; i++) {
                    int qtyToBuy = Integer.parseInt(quantities[i]);
                    psStock.setInt(1, qtyToBuy);      
                    psStock.setString(2, variantIds[i]); 
                    psStock.setInt(3, qtyToBuy);      
                    
                    int affectedRows = psStock.executeUpdate();
                    
                    if (affectedRows == 0) {
                        conn.rollback(); 
                        return false;    
                    }
                }
            }
            
            if (couponId != null && !couponId.isEmpty()) {
                try (PreparedStatement psCoupon = conn.prepareStatement(updateCoupon)) {
                    psCoupon.setString(1, userId);
                    psCoupon.setString(2, couponId);
                    psCoupon.executeUpdate();
                }
            }
            
            conn.commit(); 
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } 
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<Map<String, Object>> getOrderHistory(String userId) {
        List<Map<String, Object>> orderList = new ArrayList<>();

        String sql = "SELECT o.id, o.total_price, o.order_status, o.created_at, o.payment_method, "
                   + "a.phone_number, a.detail, a.street, a.ward, a.district, a.city "
                   + "FROM orders o "
                   + "JOIN address a ON o.address_id = a.id "
                   + "WHERE o.user_id = ? "
                   + "ORDER BY o.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> order = new HashMap<>();
                order.put("id", rs.getString("id"));
                order.put("total_price", rs.getInt("total_price"));
                order.put("order_status", rs.getString("order_status"));
                order.put("created_at", rs.getTimestamp("created_at"));
                order.put("payment_method", rs.getString("payment_method")); 
                order.put("phone_number", rs.getString("phone_number"));
                
                String fullAddress = rs.getString("detail") + ", " 
                                   + rs.getString("street") + ", " 
                                   + rs.getString("ward") + ", " 
                                   + rs.getString("district") + ", " 
                                   + rs.getString("city");
                order.put("full_address", fullAddress);
                
                orderList.add(order);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return orderList;
    }
    
    public boolean cancelOrder(String orderId, String userId) {
        // 1. Kiểm tra trạng thái đơn và lấy mã coupon
        String findOrderSql = "SELECT coupon_id, order_status FROM orders WHERE id = ? AND user_id = ?";
        String cancelOrderSql = "UPDATE orders SET order_status = 'canceled' WHERE id = ? AND user_id = ? AND order_status = 'pending'";

        String refundVoucherSql = "UPDATE user_coupon SET `limit` = `limit` + 1 WHERE user_id = ? AND coupon_id = ?";
      
        String getOrderItemsSql = "SELECT product_variant_id, amount FROM order_items WHERE order_id = ?";
        String restoreStockSql = "UPDATE product_variants SET stock = stock + ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String couponId = null;
            
            try (PreparedStatement ps = conn.prepareStatement(findOrderSql)) {
                ps.setString(1, orderId);
                ps.setString(2, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (!"pending".equals(rs.getString("order_status"))) {
                        return false; 
                    }
                    couponId = rs.getString("coupon_id");
                } else {
                    return false; 
                }
            }

            try (PreparedStatement psCancel = conn.prepareStatement(cancelOrderSql)) {
                psCancel.setString(1, orderId);
                psCancel.setString(2, userId);
                int affectedRows = psCancel.executeUpdate();
                if (affectedRows == 0) return false;
            }

            if (couponId != null && !couponId.isEmpty()) {
                try (PreparedStatement psRefund = conn.prepareStatement(refundVoucherSql)) {
                    psRefund.setString(1, userId);
                    psRefund.setString(2, couponId);
                    psRefund.executeUpdate();
                }
            }

            try (PreparedStatement psGetItems = conn.prepareStatement(getOrderItemsSql);
                 PreparedStatement psRestoreStock = conn.prepareStatement(restoreStockSql)) {
                
                psGetItems.setString(1, orderId);
                ResultSet rsItems = psGetItems.executeQuery();

                while (rsItems.next()) {
                    String variantId = rsItems.getString("product_variant_id");
                    int amountToRestore = rsItems.getInt("amount");
                    
                    psRestoreStock.setInt(1, amountToRestore);
                    psRestoreStock.setString(2, variantId); 
                    psRestoreStock.addBatch(); 
                }

                psRestoreStock.executeBatch();
            }

            conn.commit(); 
            return true;
            
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}