
package model;

public class Address {
    private int id;
    private String userId;
    private String phoneNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
    private String detail;
    
    public Address(){
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getStreet(){
        return street;
    }
    public void setStreet(String street){
        this.street = street;
    }
    public String getWard(){
        return ward;
    }
    public void setWard(String ward){
        this.ward = ward;
    }
    public String getDistrict(){
        return district;
    }
    public void setDistrict(String district){
        this.district = district;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getDetail(){
        return detail;
    }
    public void setDetail(String detail){
        this.detail = detail;
    }
    public String getFullAddress(){
        String extra = (detail != null && !detail.trim().isEmpty()) ? " (" + detail + ")" : "";
                return street + "," + ward + "," + district + "," + city + extra;
    }
}
        
    

