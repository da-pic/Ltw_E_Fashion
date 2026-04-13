/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import dao.AddressDAO;
import model.Address;
import java.util.*;
/**
 *
 * @author Chinh
 */
public class AddressService {
    private AddressDAO addressDAO = new AddressDAO();
    
    public boolean createNewAddress(Address address, String userId){
        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("Lỗi: User ID không hợp lệ.");
            return false;
        }

        if (address == null) {
            return false;
        }

        String phone = address.getPhone_number();
        if (phone == null || !phone.matches("\\d{10,11}")) {
            return false; 
        }

        if (address.getCity() == null || address.getCity().trim().isEmpty() ||
            address.getDistrict() == null || address.getDistrict().trim().isEmpty() ||
            address.getWard() == null || address.getWard().trim().isEmpty()) {
            return false;
        }

        return addressDAO.saveAddress(address, userId);
    }
    
    public List<Address> getAddressesByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("Lỗi: Không thể lấy địa chỉ vì User ID trống.");
            return new ArrayList<>(); 
        }
        
        return addressDAO.getAddressByUserId(userId);
    }
    
    public boolean deleteAddressByAddressId(int addressId){
        return addressDAO.deleteAddressByAddressId(addressId);
    }
    
    public boolean updateAddressByAddressId(Address address){
        return addressDAO.updateAddressByAddressId(address);
    }
    
}