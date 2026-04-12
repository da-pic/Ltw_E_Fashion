/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Chinh
 */
public class Address {
    private int id;
    private String user_id;
    private String phone_number;
    private String street;
    private String ward;
    private String district;
    private String city;
    private String detail;

    public Address(String user_id, String phone_number, String street, String ward, String district, String city, String detail) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.detail = detail;
    }

    public Address() {
       
    }

    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getStreet() {
        return street;
    }

    public String getWard() {
        return ward;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getDetail() {
        return detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
}
