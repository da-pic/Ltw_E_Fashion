package dao;

import model.User;
import java.sql.*;
import util.DatabaseConnection;
public class UserDAO {
    
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("id"), rs.getString("name"), rs.getDate("birthdate"),
                    rs.getString("phonenumber"), rs.getString("gender"),
                    rs.getString("username"), rs.getString("password_hash"), rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public User findByUserID(String userID) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDate("birthdate"),
                    rs.getString("phonenumber"),
                    rs.getString("gender"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean save(User user) {
        String sql = "INSERT INTO user (id, name, birthdate, phonenumber, gender, "
                + "username, password_hash, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setDate(3, user.getBirthdate());
            stmt.setString(4, user.getPhonenumber());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getUsername());
            stmt.setString(7, user.getPasswordHash());
            stmt.setBoolean(8, true); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getUserRoleByID(String userID) {
        String role = null;
        String sql = "SELECT r.name " +
                     "FROM user_role ur " +
                     "JOIN roles r ON ur.role_id = r.id " +
                     "WHERE ur.user_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                role = rs.getString("name");
            }
        } catch (Exception e) {
        }

        return role;
    }
}