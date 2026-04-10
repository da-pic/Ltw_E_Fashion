
package dao;

import model.Coupon;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import util.DatabaseConnection;
import java.sql.*;

public class CouponDAO {
    
    public List<Coupon> getAllCoupon() {
        List<Coupon> list = new ArrayList<>();
        String sql = "SELECT * FROM coupon";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
    
    public List<Coupon> getUserCoupons (String userID)  {
        List<Coupon> list = new ArrayList<>();

        String sql = "SELECT c.* " +
                 "FROM user_coupon uc " +
                 "JOIN coupon c ON uc.coupon_id = c.id " +
                 "WHERE uc.user_id = ?";  
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
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
    
    public Coupon getCouponById(String couponID) {

        String sql = "SELECT * FROM coupon WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, couponID);

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

        String sql = "UPDATE coupon SET type = ?, value = ?, max_discount = ?, min_cost = ?, " +
                     "started_date = ?, expired_date = ?, is_active = ? WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
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
    
    public boolean removeCoupon(String couponID) {
        String sql = "UPDATE coupon SET is_active = FALSE WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, couponID);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean addCoupon(Coupon coupon) {

        String sql = "INSERT INTO coupon (id, type, value, max_discount, min_cost, "
                    + "started_date, expired_date, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Date now = new Date(System.currentTimeMillis());

            boolean isActive = !now.before(coupon.getStartedDate()) 
                            && !now.after(coupon.getExpiredDate());

            ps.setString(1, coupon.getId());
            ps.setString(2, coupon.getType());
            ps.setInt(3, coupon.getValue());
            ps.setInt(4, coupon.getMaxDiscount());
            ps.setInt(5, coupon.getMinCost());
            ps.setDate(6, new Date(coupon.getStartedDate().getTime()));
            ps.setDate(7, new Date(coupon.getExpiredDate().getTime()));
            ps.setBoolean(8, isActive);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    
    public Integer getLimitUserCoupons(String userID, String couponID) {

        String sql = "SELECT `limit` FROM user_coupon WHERE user_id = ? AND coupon_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, couponID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("limit");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean addUserCoupon(String userID, String couponID, int amount) {

        String sql = "INSERT INTO user_coupon (user_id, coupon_id, `limit`) " +
                     "VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE `limit` = `limit` + VALUES(`limit`)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, couponID);
            ps.setInt(3, amount);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean updateUserCoupon(String userID, String couponID, int delta) {

        String sql = "UPDATE user_coupon SET `limit` = `limit` + ? " +
                     "WHERE user_id = ? AND coupon_id = ? AND `limit` + ? >= 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, delta);
            ps.setString(2, userID);
            ps.setString(3, couponID);
            ps.setInt(4, delta); 
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
}
