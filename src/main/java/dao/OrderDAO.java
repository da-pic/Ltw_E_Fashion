package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderDetail;
import util.DatabaseConnection;

public class OrderDAO {

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.id, o.user_id, o.total_price, o.status, " +
                     "u.name AS receiver_name, " +
                     "a.phone_number AS receiver_phone, " +
                     "CONCAT_WS(', ', NULLIF(a.detail, ''), a.street, a.ward, a.district, a.city) AS detail_address " +
                     "FROM orders o " +
                     "LEFT JOIN user u ON o.user_id = u.id " +
                     "LEFT JOIN address a ON o.address_id = a.id " +
                     "ORDER BY o.id DESC";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                String dbStatus = rs.getString("status");
                String displayStatus = "Chờ xác nhận"; 
                if ("delivered".equals(dbStatus)) displayStatus = "Hoàn thành";
                else if ("shipping".equals(dbStatus)) displayStatus = "Đang giao";
                else if ("canceled".equals(dbStatus)) displayStatus = "Đã hủy";
                else if ("pending".equals(dbStatus)) displayStatus = "Chờ xác nhận";

                Order order = new Order();
                order.setId(rs.getString("id"));
                order.setUserId(rs.getString("user_id"));
                
                order.setCustomerName(rs.getString("receiver_name")); 
                order.setPhone(rs.getString("receiver_phone"));     
                order.setAddress(rs.getString("detail_address"));   
                
                order.setTotalAmount(rs.getInt("total_price"));     
                order.setStatus(displayStatus);
                order.setOrderDate(null); 
                
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order getOrderById(String orderId) {
        Order order = null;
        String sqlOrder = "SELECT o.id, o.user_id, o.total_price, o.status, " +
                          "u.name AS receiver_name, " +
                          "a.phone_number AS receiver_phone, " +
                          "CONCAT_WS(', ', NULLIF(a.detail, ''), a.street, a.ward, a.district, a.city) AS detail_address " +
                          "FROM orders o " +
                          "LEFT JOIN user u ON o.user_id = u.id " +
                          "LEFT JOIN address a ON o.address_id = a.id " +
                          "WHERE o.id = ?";
                          
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlOrder)) {
             
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dbStatus = rs.getString("status");
                String displayStatus = "Chờ xác nhận";
                if ("delivered".equals(dbStatus)) displayStatus = "Hoàn thành";
                else if ("shipping".equals(dbStatus)) displayStatus = "Đang giao";
                else if ("canceled".equals(dbStatus)) displayStatus = "Đã hủy";
                else if ("pending".equals(dbStatus)) displayStatus = "Chờ xác nhận";

                order = new Order();
                order.setId(rs.getString("id"));
                order.setUserId(rs.getString("user_id"));
                order.setCustomerName(rs.getString("receiver_name"));
                order.setPhone(rs.getString("receiver_phone"));
                order.setAddress(rs.getString("detail_address"));
                order.setTotalAmount(rs.getInt("total_price"));
                order.setStatus(displayStatus);
                order.setOrderDate(null);
                
                order.setOrderDetails(getOrderDetailsByOrderId(orderId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    private List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT d.product_variant_id, d.amount, d.unit_price, " +
                     "p.product_name, v.color, v.size, v.image " +
                     "FROM order_items d " +
                     "JOIN product_variants v ON d.product_variant_id = v.id " +
                     "JOIN product p ON v.product_id = p.id " +
                     "WHERE d.order_id = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderDetail detail = new OrderDetail(
                    0, 
                    orderId, 
                    rs.getString("product_variant_id"),
                    rs.getInt("amount"), 
                    rs.getInt("unit_price")
                );
                detail.setProductName(rs.getString("product_name"));
                detail.setColor(rs.getString("color"));
                detail.setSize(rs.getString("size"));
                detail.setImage(rs.getString("image"));
                
                list.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateOrderStatus(String orderId, String status) {
        String dbStatus = "pending";
        if ("Hoàn thành".equals(status)) dbStatus = "delivered";
        else if ("Đang giao".equals(status)) dbStatus = "shipping";
        else if ("Đã hủy".equals(status)) dbStatus = "canceled";

        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, dbStatus);
            stmt.setString(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}