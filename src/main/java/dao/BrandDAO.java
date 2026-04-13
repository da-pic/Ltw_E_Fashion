package dao;

import java.sql.*;
import java.util.*;
import model.Brand;
import util.DatabaseConnection;

public class BrandDAO {

    public List<Brand> getAllBrands() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT * FROM brand";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Brand(
                    rs.getString("brand_id"),
                    rs.getString("brand_name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public String getBrandNameByID(String brandId) {
        String sql = "SELECT brand_name FROM brand WHERE brand_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, brandId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("brand_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public String addNewBrand(String brandName) {
        String sql = "INSERT INTO brand (brand_id, brand_name) VALUES (?, ?)";

        String uuid = java.util.UUID.randomUUID().toString();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid);
            stmt.setString(2, brandName);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return uuid;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}