/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.SalaryLog;
import java.util.List;
import java.util.ArrayList;
import util.DatabaseConnection;
import java.sql.*;

public class SalaryLogDAO {
    public List<SalaryLog> getEmployeeFineByMonth(String employeeID, int month, int year) {

        List<SalaryLog> list = new ArrayList<>();

        String sql = "SELECT * FROM salary_log " +
                     "WHERE employee_id = ? AND type = 'fine' " +
                     "AND MONTH(created_at) = ? AND YEAR(created_at) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employeeID);
            ps.setInt(2, month);
            ps.setInt(3, year);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SalaryLog log = new SalaryLog();
                log.setId(rs.getString("id"));
                log.setEmployeeId(rs.getString("employee_id"));
                log.setAmount(rs.getInt("amount"));
                log.setType(rs.getString("type"));
                log.setCreatedAt(rs.getDate("created_at"));
                log.setDescription(rs.getString("description"));

                list.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<SalaryLog> getEmployeeBonusByMonth(String employeeID, int month, int year) {

        List<SalaryLog> list = new ArrayList<>();

        String sql = "SELECT * FROM salary_log " +
                     "WHERE employee_id = ? AND type = 'bonus' " +
                     "AND MONTH(created_at) = ? AND YEAR(created_at) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employeeID);
            ps.setInt(2, month);
            ps.setInt(3, year);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SalaryLog log = new SalaryLog();
                log.setId(rs.getString("id"));
                log.setEmployeeId(rs.getString("employee_id"));
                log.setAmount(rs.getInt("amount"));
                log.setType(rs.getString("type"));
                log.setCreatedAt(rs.getDate("created_at"));
                log.setDescription(rs.getString("description"));

                list.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean addSalaryLog(SalaryLog log) {

        String sql = "INSERT INTO salary_log (id, employee_id, amount, type, created_at, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, log.getId());
            ps.setString(2, log.getEmployeeId());
            ps.setInt(3, log.getAmount());
            ps.setString(4, log.getType());
            ps.setDate(5, log.getCreatedAt());
            ps.setString(6, log.getDescription());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteFromSalaryLog(String id) {

        String sql = "DELETE FROM salary_log WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean updateSalaryLog(SalaryLog log) {

        String sql = "UPDATE salary_log SET employee_id = ?, amount = ?, type = ?, created_at = ?, description = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, log.getEmployeeId());
            ps.setInt(2, log.getAmount());
            ps.setString(3, log.getType());
            ps.setDate(4, log.getCreatedAt());
            ps.setString(5, log.getDescription());
            ps.setString(6, log.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
