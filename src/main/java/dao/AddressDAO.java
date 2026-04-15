/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.Address;
import java.sql.*;
import util.DatabaseConnection;
import java.util.*;
/**
 *
 * @author Chinh
 */
public class AddressDAO {
    public boolean saveAddress(Address address, String userId){
        String sql = "INSERT INTO address (user_id, phone_number, street, ward, district, city, detail) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, userId);
            stmt.setString(2, address.getPhone_number());
            stmt.setString(3, address.getStreet());
            stmt.setString(4, address.getWard());
            stmt.setString(5, address.getDistrict());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getDetail());
            return stmt.executeUpdate() > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
            return false;
    }
    
    public boolean existedAddressWhenAdd(Address address, String userId){
        String sql = "SELECT * FROM address WHERE user_id = ? AND phone_number = ? "
                + "AND street = ? AND ward = ? AND district = ? AND city = ? "
                + "AND detail = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, userId);
            stmt.setString(2, address.getPhone_number());
            stmt.setString(3, address.getStreet());
            stmt.setString(4, address.getWard());
            stmt.setString(5, address.getDistrict());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getDetail());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean existedAddressWhenUpdate(Address address){
        String sql = "SELECT * FROM address WHERE user_id = ? AND phone_number = ? "
                + "AND street = ? AND ward = ? AND district = ? AND city = ? "
                + "AND detail = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, address.getUser_id());
            stmt.setString(2, address.getPhone_number());
            stmt.setString(3, address.getStreet());
            stmt.setString(4, address.getWard());
            stmt.setString(5, address.getDistrict());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getDetail());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Address> getAddressByUserId(String userId){
        String sql = "SELECT * FROM address a JOIN users u ON a.user_id = u.id WHERE u.id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<Address> userAddress = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String user_id = rs.getString("user_id");
                String phone_number = rs.getString("phone_number");
                String street = rs.getString("street");
                String ward = rs.getString("ward");
                String district = rs.getString("district");
                String city = rs.getString("city");
                String detail = rs.getString("detail");
                
                Address address = new Address(user_id, phone_number, street, ward, district, city, detail);
                address.setId(id);
                userAddress.add(address);
            }
            return userAddress;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteAddressByAddressId(int addressId){
        String sql = "DELETE FROM address WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, addressId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateAddressByAddressId(Address address){
        String sql = "UPDATE address SET phone_number = ?, street = ?, ward = ?, district = ?, city = ?, detail = ? WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, address.getPhone_number());
            stmt.setString(2, address.getStreet());
            stmt.setString(3, address.getWard());
            stmt.setString(4, address.getDistrict());
            stmt.setString(5, address.getCity());
            stmt.setString(6, address.getDetail());
            stmt.setInt(7, address.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
