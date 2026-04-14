package dao;

import model.Salary;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAO {

    // Staff xem lương của chính mình
    public List<Salary> getSalaryByUserId(String userId) {
        List<Salary> list = new ArrayList<>();
    String sql = "SELECT s.*, u.name AS staff_name " +
             "FROM salary s " +
             "JOIN user u ON s.user_id = u.id " +
             "WHERE s.user_id = ? " +
             "ORDER BY s.year DESC, s.month DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Salary s = mapRow(rs);
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Admin xem tất cả lương nhân viên
    public List<Salary> getAllSalaries() {
        List<Salary> list = new ArrayList<>();
        String sql = "SELECT s.*, u.name AS staff_name " +
             "FROM salary s " +
             "JOIN user u ON s.user_id = u.id " +
             "ORDER BY s.year DESC, s.month DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Salary s = mapRow(rs);
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Salary mapRow(ResultSet rs) throws SQLException {
        Salary s = new Salary();
        s.setId(rs.getInt("id"));
        s.setUserId(rs.getString("user_id"));
        s.setMonth(rs.getInt("month"));
        s.setYear(rs.getInt("year"));
        s.setBaseSalary(rs.getLong("base_salary"));
        s.setBonus(rs.getLong("bonus"));
        s.setDeduction(rs.getLong("deduction"));
        s.setNote(rs.getString("note"));
        s.setStaffName(rs.getString("staff_name"));
        return s;
    }
}