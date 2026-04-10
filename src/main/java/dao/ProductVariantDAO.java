package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ProductVariant;
import util.DatabaseConnection;

public class ProductVariantDAO {
    public List<ProductVariant> getProductVariantsByProductId(String productId){
        String sql = "SELECT * FROM product_variants WHERE product_id = ?";
        List<ProductVariant> productVariantsList = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id");
                String product_id = rs.getString("product_id");
                String color = rs.getString("color");
                String size = rs.getString("size");
                String image = rs.getString("image");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                Boolean is_active = rs.getBoolean("is_active");
                
                ProductVariant productVariants = new ProductVariant(id, product_id, color, size, image, price, stock, is_active);
                productVariantsList.add(productVariants);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return productVariantsList;
    }

    public boolean insertVariant(ProductVariant variant) {
        String sql = "INSERT INTO product_variants (id, product_id, color, size, price, stock, is_active) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, variant.getId());
            ps.setString(2, variant.getProduct_id()); 
            ps.setString(3, variant.getColor());
            ps.setString(4, variant.getSize());
            ps.setInt(5, variant.getPrice());
            ps.setInt(6, variant.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVariant(ProductVariant variant) {
        String sql = "UPDATE product_variants SET color = ?, size = ?, price = ?, stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, variant.getColor());
            ps.setString(2, variant.getSize());
            ps.setInt(3, variant.getPrice());
            ps.setInt(4, variant.getStock());
            ps.setString(5, variant.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVariant(String id) {
        String sql = "DELETE FROM product_variants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
