package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;
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
        String sql = "INSERT INTO users (id, name, birthdate, phonenumber, gender, "
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
    
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, birthdate = ?, phonenumber = ?, gender = ?, is_active = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setDate(2, user.getBirthdate());
            stmt.setString(3, user.getPhonenumber());
            stmt.setString(4, user.getGender());
            stmt.setBoolean(5, user.isActive());
            stmt.setString(6, user.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDate("birthdate"),
                    rs.getString("phonenumber"),
                    rs.getString("gender"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getBoolean("is_active")
                );

                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean updateUserStatus(String userId, boolean status) {
        String sql = "UPDATE users SET is_active = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, status);
            stmt.setString(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
   
    public boolean assignRoleAndEmployee(String userId, String roleName) {
        String sqlRole = "INSERT INTO user_role (user_id, role_id) SELECT ?, id FROM roles WHERE name = ?";
        String sqlEmployee = "INSERT INTO employee (employee_id, base_salary) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); 

            // 1. Thêm vào bảng user_role
            try (PreparedStatement stmtRole = conn.prepareStatement(sqlRole)) {
                stmtRole.setString(1, userId);
                stmtRole.setString(2, roleName);
                stmtRole.executeUpdate();
            }

            if ("Employee".equalsIgnoreCase(roleName) || "Admin".equalsIgnoreCase(roleName)) {
                try (PreparedStatement stmtEmp = conn.prepareStatement(sqlEmployee)) {
                    stmtEmp.setString(1, userId);
                    stmtEmp.setInt(2, 5000000); 
                    stmtEmp.executeUpdate();
                }
            }

            conn.commit(); 
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}