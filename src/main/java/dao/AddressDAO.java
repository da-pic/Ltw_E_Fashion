package dao;

import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public List<Address> getAddressesByUserId(String userId) throws Exception {
        List<Address> list = new ArrayList<>();

        String sql = "SELECT * FROM address WHERE user_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Address a = new Address();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getString("user_id"));
                    a.setPhoneNumber(rs.getString("phone_number"));
                    a.setStreet(rs.getString("street"));
                    a.setWard(rs.getString("ward"));
                    a.setDistrict(rs.getString("district"));
                    a.setCity(rs.getString("city"));
                    a.setDetail(rs.getString("detail"));
                    list.add(a);
                }
            }
        }

        return list;
    }

    public Address getAddressByIdAndUserId(int addressId, String userId) throws Exception {
        String sql = "SELECT * FROM address WHERE id = ? AND user_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addressId);
            ps.setString(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Address a = new Address();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getString("user_id"));
                    a.setPhoneNumber(rs.getString("phone_number"));
                    a.setStreet(rs.getString("street"));
                    a.setWard(rs.getString("ward"));
                    a.setDistrict(rs.getString("district"));
                    a.setCity(rs.getString("city"));
                    a.setDetail(rs.getString("detail"));
                    return a;
                }
            }
        }

        return null;
    }
}