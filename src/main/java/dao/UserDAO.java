package dao;

import model.User;
import java.sql.*;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/e_fashion";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean save(User user) {
        String sql = "INSERT INTO user (id, name, birthdate, phonenumber, gender, username, password_hash, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
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
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}