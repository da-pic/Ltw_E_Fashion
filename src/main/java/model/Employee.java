package model;
import java.sql.Date;
public class Employee {
    private String employeeId;
    private String employeeName;
    private String employeePhoneNumber;
    private Date employeeBirthDate;
    private int baseSalary;
    private String role;

    public Employee() {}
    
    public Employee(String employeeId, String employeeName, String employeePhoneNumber,
                    Date employeeBirthDate, int baseSalary, String role) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeBirthDate = employeeBirthDate;
        this.baseSalary = baseSalary;
        this.role = role;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public Date getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    public void setEmployeeBirthDate(Date employeeBirthDate) {
        this.employeeBirthDate = employeeBirthDate;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}