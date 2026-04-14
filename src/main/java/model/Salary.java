package model;

public class Salary {
    private int id;
    private String userId;
    private int month;
    private int year;
    private long baseSalary;
    private long bonus;
    private long deduction;
    private String note;
    private String staffName; // JOIN từ bảng user

    public Salary() {}

    public Salary(int id, String userId, int month, int year,
                  long baseSalary, long bonus, long deduction, String note) {
        this.id = id;
        this.userId = userId;
        this.month = month;
        this.year = year;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.deduction = deduction;
        this.note = note;
    }

    public long getNetSalary() {
        return baseSalary + bonus - deduction;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public long getBaseSalary() { return baseSalary; }
    public void setBaseSalary(long baseSalary) { this.baseSalary = baseSalary; }

    public long getBonus() { return bonus; }
    public void setBonus(long bonus) { this.bonus = bonus; }

    public long getDeduction() { return deduction; }
    public void setDeduction(long deduction) { this.deduction = deduction; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
}