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
                c.setMaxDiscount(rs.getInt("max_discount"));
                c.setMinCost(rs.getInt("min_cost"));
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
    
    public List<Coupon> getAllCoupons() {
        List<Coupon> list = new ArrayList<>();

        String sql = "SELECT * FROM coupon ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Coupon c = new Coupon();

                c.setId(rs.getString("id"));
                c.setType(rs.getString("type"));
                c.setValue(rs.getInt("value"));
                c.setMaxDiscount(rs.getInt("max_discount"));
                c.setMinCost(rs.getInt("min_cost"));
                c.setStartedDate(rs.getDate("started_date"));
                c.setExpiredDate(rs.getDate("expired_date"));
                c.setActive(rs.getBoolean("is_active"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean deleteCoupon(String id) {
        String sql = "UPDATE coupon SET is_active = 0 WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    

    public Coupon getCouponById(String id) {
        String sql = "SELECT * FROM coupon WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Coupon c = new Coupon();
                c.setId(rs.getString("id"));
                c.setType(rs.getString("type"));
                c.setValue(rs.getInt("value"));
                c.setMaxDiscount(rs.getInt("max_discount"));
                c.setMinCost(rs.getInt("min_cost"));
                c.setStartedDate(rs.getDate("started_date"));
                c.setExpiredDate(rs.getDate("expired_date"));
                c.setActive(rs.getBoolean("is_active"));

                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean updateCoupon(Coupon coupon) {
        String sql = "UPDATE coupon SET type=?, value=?, max_discount=?, min_cost=?, "
                   + "started_date=?, expired_date=?, is_active=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coupon.getType());
            ps.setInt(2, coupon.getValue());
            ps.setInt(3, coupon.getMaxDiscount());
            ps.setInt(4, coupon.getMinCost());
            ps.setDate(5, new Date(coupon.getStartedDate().getTime()));
            ps.setDate(6, new Date(coupon.getExpiredDate().getTime()));
            ps.setBoolean(7, coupon.IsActive());
            ps.setString(8, coupon.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean addCoupon(Coupon coupon) {

        String sql = "INSERT INTO coupon (id, type, value, max_discount, min_cost, " +
                     "started_date, expired_date, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Lấy thời gian hiện tại
            java.util.Date now = new java.util.Date();

            // Tự tính trạng thái active
            boolean isActive = !now.before(coupon.getStartedDate()) 
                            && !now.after(coupon.getExpiredDate());

            ps.setString(1, coupon.getId());
            ps.setString(2, coupon.getType());
            ps.setInt(3, coupon.getValue());
            ps.setInt(4, coupon.getMaxDiscount());
            ps.setInt(5, coupon.getMinCost());
            ps.setDate(6, new java.sql.Date(coupon.getStartedDate().getTime()));
            ps.setDate(7, new java.sql.Date(coupon.getExpiredDate().getTime()));
            ps.setBoolean(8, isActive);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}