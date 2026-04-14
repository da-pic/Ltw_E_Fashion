
package dao;

import model.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO {

    public List<Coupon> getAvailableCouponsByUserId(String userId) throws Exception {
        List<Coupon> list = new ArrayList<>();

        String sql = "SELECT c.*, uc.`limit` AS user_limit " +
                "FROM user_coupon uc " +
                "JOIN coupon c ON uc.coupon_id = c.id " +
                "WHERE uc.user_id = ? " +
                "AND uc.`limit` > 0 " +
                "AND c.is_active = 1 " +
                "AND CURDATE() BETWEEN c.started_date AND c.expired_date";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
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
                    c.setUserLimit(rs.getInt("user_limit"));
                    list.add(c);
                }
            }
        }

        return list;
    }

    public Coupon getValidCouponOfUser(String userId, String couponId) throws Exception {
        String sql = "SELECT c.*, uc.`limit` AS user_limit " +
                "FROM user_coupon uc " +
                "JOIN coupon c ON uc.coupon_id = c.id " +
                "WHERE uc.user_id = ? " +
                "AND c.id = ? " +
                "AND uc.`limit` > 0 " +
                "AND c.is_active = 1 " +
                "AND CURDATE() BETWEEN c.started_date AND c.expired_date";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ps.setString(2, couponId);

            try (ResultSet rs = ps.executeQuery()) {
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
                    c.setUserLimit(rs.getInt("user_limit"));
                    return c;
                }
            }
        }

        return null;
    }

    public void decreaseUserCouponLimit(Connection conn, String userId, String couponId) throws Exception {
        String sql = "UPDATE user_coupon SET `limit` = `limit` - 1 " +
                "WHERE user_id = ? AND coupon_id = ? AND `limit` > 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, couponId);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new Exception("Không thể cập nhật lượt dùng coupon.");
            }
        }
    }
}