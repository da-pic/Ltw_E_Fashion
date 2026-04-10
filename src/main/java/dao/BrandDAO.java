package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Brand;
import util.DatabaseConnection;

public class BrandDAO {
    public List<Brand> getAllBrands() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT * FROM brand";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Brand(rs.getString("brand_id"), rs.getString("brand_name"), rs.getString("logo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}