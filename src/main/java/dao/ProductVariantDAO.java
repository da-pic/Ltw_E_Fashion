package dao;

import model.ProductVariant;
import java.util.*;
import java.sql.*;
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
    
    
    public ProductVariant getProductVariantsById(String productVariantID) {
        String sql = "SELECT * FROM product_variants WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productVariantID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ProductVariant(
                    rs.getString("id"),
                    rs.getString("product_id"),
                    rs.getString("color"),
                    rs.getString("size"),
                    rs.getString("image"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean addProductVariants(ProductVariant pv) {

        String sql = "INSERT INTO product_variants (id, product_id, color, size, image, price, stock, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pv.getId());
            stmt.setString(2, pv.getProduct_id());
            stmt.setString(3, pv.getColor());
            stmt.setString(4, pv.getSize());
            stmt.setString(5, pv.getImage());
            stmt.setInt(6, pv.getPrice());
            stmt.setInt(7, pv.getStock());
            stmt.setBoolean(8, pv.getIs_active());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteProductVariant(String productVariantID) {

        String sql = "UPDATE product_variants SET is_active = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productVariantID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean updateProductVariant(String productVariantID, int amount) {

        String sql = "UPDATE product_variants SET stock = stock + ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, amount);
            stmt.setString(2, productVariantID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
