package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import util.DatabaseConnection;

public class DashboardDAO {

    public int getTotalActiveProducts() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM product WHERE is_active = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }

    public int getTotalUsers() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM user WHERE is_active = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }

    public int getPendingOrders() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status = 'pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }

    public long getTotalRevenue() {
        long total = 0;
        String sql = "SELECT SUM(total_price) FROM orders WHERE status = 'delivered'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) total = rs.getLong(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return total;
    }

    public Map<String, Integer> getOrderStatusData() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as count FROM orders GROUP BY status";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                map.put(rs.getString("status"), rs.getInt("count"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return map;
    }

    public Map<String, Integer> getProductsByCategory() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT c.name, COUNT(p.id) as count " +
                     "FROM product p " +
                     "JOIN category c ON p.category_id = c.id " +
                     "WHERE p.is_active = 1 " +
                     "GROUP BY c.name";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                map.put(rs.getString("name"), rs.getInt("count"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return map;
    }
}