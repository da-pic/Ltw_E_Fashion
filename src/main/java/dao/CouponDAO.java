package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Coupon;
import util.DatabaseConnection;

public class CouponDAO {

    public Coupon getValidCoupon(String userId, String couponId, int currentTotal) {
        String sql = "SELECT c.* FROM coupon c "
                   + "JOIN user_coupon uc ON c.id = uc.coupon_id "
                   + "WHERE uc.user_id = ? AND c.id = ? AND c.is_active = 1 "
                   + "AND c.started_date <= CURRENT_DATE AND c.expired_date >= CURRENT_DATE "
                   + "AND c.min_cost <= ? AND uc.`limit` > 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ps.setString(2, couponId);
            ps.setInt(3, currentTotal);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Coupon c = new Coupon();
                c.setId(rs.getString("id"));
                c.setType(rs.getString("type")); 
                c.setValue(rs.getInt("value"));
                c.setMax_discount(rs.getInt("max_discount"));
                c.setMin_cost(rs.getInt("min_cost"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public List<Map<String, Object>> getMyVouchers(String userId) {
        List<Map<String, Object>> list = new ArrayList<>();

        String sql = "SELECT c.*, uc.`limit` as usage_limit "
                   + "FROM coupon c "
                   + "JOIN user_coupon uc ON c.id = uc.coupon_id "
                   + "WHERE uc.user_id = ? AND c.is_active = 1 AND uc.`limit` > 0 "
                   + "ORDER BY c.expired_date ASC";
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> voucher = new HashMap<>();
                voucher.put("id", rs.getString("id"));
                voucher.put("type", rs.getString("type"));
                voucher.put("value", rs.getInt("value"));
                voucher.put("max_discount", rs.getInt("max_discount"));
                voucher.put("min_cost", rs.getInt("min_cost"));
                voucher.put("started_date", rs.getDate("started_date"));
                voucher.put("expired_date", rs.getDate("expired_date"));
                voucher.put("usage_limit", rs.getInt("usage_limit")); 

                Date expired = rs.getDate("expired_date");
                boolean isExpired = expired.before(new java.util.Date());
                voucher.put("is_expired", isExpired);
                
                list.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}