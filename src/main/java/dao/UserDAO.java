package dao;

import model.User;
import java.sql.*;
import util.DatabaseConnection;
import java.time.LocalDateTime;
public class UserDAO {
    
    //tìm role và check xem username đã tồn tại chưa
    public User findByUsername(String username) {
        String sql = "SELECT u.*, r.name as role_name FROM users u " +
                     "LEFT JOIN user_role ur ON u.id = ur.user_id " +
                     "LEFT JOIN roles r ON ur.role_id = r.id " +
                     "WHERE u.username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                    rs.getString("id"), rs.getString("name"), rs.getDate("birthdate"),
                    rs.getString("phonenumber"), rs.getString("gender"),
                    rs.getString("username"), rs.getString("password_hash"), 
                    rs.getBoolean("is_active")
                );
                user.setRole(rs.getString("role_name")); 
                return user;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Lưu User Role khi đăng ký
    public void insertUserRole(String userId, String roleId) throws SQLException {
        String sql = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, roleId);
            stmt.executeUpdate();
        }
    }

    //lưu User
    public boolean save(User user) {
        String sql = "INSERT INTO users (id, name, birthdate, phonenumber, gender, username, password_hash, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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

    //Update thông tin của người dùng
    public boolean updateInformationByUserId(User user){
        String sql = "UPDATE users SET name = ?, birthdate = ?, gender = ? WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
           stmt.setString(1, user.getName());
           stmt.setDate(2, user.getBirthdate());
           stmt.setString(3, user.getGender());
           stmt.setString(4, user.getId()); 

           int rowsAffected = stmt.executeUpdate();
           return rowsAffected > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}