package model;

import java.util.List;
import java.sql.Date;

public class User {
    private String id;
    private String name;
    private Date birthdate;
    private String phonenumber;
    private String gender;
    private String username;
    private String passwordHash;
    private boolean isActive;
    
    private String role; // 🔥 thêm role
    
    private List<Coupon> userCoupons;

    public User() {}

    public User(String id, String name, Date birthdate, String phonenumber, 
                String gender, String username, String passwordHash, boolean isActive) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.phonenumber = phonenumber;
        this.gender = gender;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public void setUserCoupons(List<Coupon> coupons){
        this.userCoupons = coupons;
    }

    public List<Coupon> getUserCoupons(){
        return userCoupons;
    }
}