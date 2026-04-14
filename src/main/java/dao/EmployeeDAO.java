
package dao;
import model.Employee;
import java.util.*;
import java.sql.*;
import util.DatabaseConnection;
public class EmployeeDAO {
    
    public Employee getEmployeeById(String employeeID) {
        String sql = "SELECT u.id, u.name, u.phonenumber, u.birthdate, " +
                     "e.base_salary, r.name AS role " +
                     "FROM users u " +
                     "JOIN employee e ON u.id = e.employee_id " +
                     "LEFT JOIN user_role ur ON u.id = ur.user_id " +
                     "LEFT JOIN roles r ON ur.role_id = r.id " +
                     "WHERE u.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employeeID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee();

                emp.setEmployeeId(rs.getString("id"));
                emp.setEmployeeName(rs.getString("name"));
                emp.setEmployeePhoneNumber(rs.getString("phonenumber"));
                emp.setEmployeeBirthDate(rs.getDate("birthdate"));

                emp.setBaseSalary(rs.getInt("base_salary"));
                emp.setRole(rs.getString("role"));

                return emp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Employee> getAllEmployees() {

        List<Employee> list = new ArrayList<>();

        String sql = "SELECT u.id, u.name, u.phonenumber, u.birthdate, " +
                     "e.base_salary, r.name AS role " +
                     "FROM users u " +
                     "JOIN employee e ON u.id = e.employee_id " +
                     "LEFT JOIN user_role ur ON u.id = ur.user_id " +
                     "LEFT JOIN roles r ON ur.role_id = r.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee();

                emp.setEmployeeId(rs.getString("id"));
                emp.setEmployeeName(rs.getString("name"));
                emp.setEmployeePhoneNumber(rs.getString("phonenumber"));
                emp.setEmployeeBirthDate(rs.getDate("birthdate"));
                emp.setBaseSalary(rs.getInt("base_salary"));
                emp.setRole(rs.getString("role"));

                list.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean updateEmployee(Employee emp) {

        String updateUserSQL = "UPDATE users SET name = ?, birthdate = ?, phonenumber = ? WHERE id = ?";
        String updateEmployeeSQL = "UPDATE employee SET base_salary = ? WHERE employee_id = ?";
        String updateRoleSQL = "UPDATE user_role SET role_id = (SELECT id FROM roles WHERE name = ?) WHERE user_id = ?";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement ps = conn.prepareStatement(updateUserSQL)) {
                ps.setString(1, emp.getEmployeeName());
                ps.setDate(2, emp.getEmployeeBirthDate());
                ps.setString(3, emp.getEmployeePhoneNumber());
                ps.setString(4, emp.getEmployeeId());
                ps.executeUpdate();
            }

            // 2. Update employee
            try (PreparedStatement ps = conn.prepareStatement(updateEmployeeSQL)) {
                ps.setInt(1, emp.getBaseSalary());
                ps.setString(2, emp.getEmployeeId());
                ps.executeUpdate();
            }

            // 3. Update role
            try (PreparedStatement ps = conn.prepareStatement(updateRoleSQL)) {
                ps.setString(1, emp.getRole()); // ví dụ: "admin"
                ps.setString(2, emp.getEmployeeId());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean addEmployee(Employee emp) {

        String insertUserSQL = "INSERT INTO users (id, name, birthdate, phonenumber, is_active) VALUES (?, ?, ?, ?, TRUE)";
        String insertEmployeeSQL = "INSERT INTO employee (employee_id, base_salary) VALUES (?, ?)";
        String insertRoleSQL = "INSERT INTO user_role (user_id, role_id) VALUES (?, (SELECT id FROM roles WHERE name = ?))";

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. insert users
            try (PreparedStatement ps = conn.prepareStatement(insertUserSQL)) {
                ps.setString(1, emp.getEmployeeId());
                ps.setString(2, emp.getEmployeeName());
                ps.setDate(3, emp.getEmployeeBirthDate());
                ps.setString(4, emp.getEmployeePhoneNumber());
                ps.executeUpdate();
            }

            // 2. insert employee
            try (PreparedStatement ps = conn.prepareStatement(insertEmployeeSQL)) {
                ps.setString(1, emp.getEmployeeId());
                ps.setInt(2, emp.getBaseSalary());
                ps.executeUpdate();
            }

            // 3. insert role
            try (PreparedStatement ps = conn.prepareStatement(insertRoleSQL)) {
                ps.setString(1, emp.getEmployeeId());
                ps.setString(2, emp.getRole());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteEmployee(String employeeID) {

        String sql = "UPDATE users SET is_active = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employeeID);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean FineEmployee(String employeeID, int amount, String description){
        String sql = "INSERT INTO salary_log (id, employee_id, amount, type, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, java.util.UUID.randomUUID().toString());
            ps.setString(2, employeeID);
            ps.setInt(3, amount);
            ps.setString(4, "fine");
            ps.setString(5, description);
            
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;        
    }
    
    public boolean BonusEmployee(String employeeID, int amount, String description){
        String sql = "INSERT INTO salary_log (id, employee_id, amount, type, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, java.util.UUID.randomUUID().toString());
            ps.setString(2, employeeID);
            ps.setInt(3, amount);
            ps.setString(4, "bonus");
            ps.setString(5, description);
            
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;         
    }
}
