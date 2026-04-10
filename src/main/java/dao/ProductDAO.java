/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import util.DatabaseConnection;
/**
 *
 * @author Chinh
 */
public class ProductDAO {
    public List<Product> getAllProduct(){
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.id, "
                + "p.product_name, "
                + "p.brand_id, "
                + "p.category_id, "
                + "p.is_active, "
                + "p.description, "
                + "MIN(v.price) AS display_price, "
                + "MAX(v.price) AS display_max_price, "
                + "(SELECT image FROM product_variants WHERE product_id = p.id LIMIT 1) AS display_image "
                + "FROM product p "
                + "LEFT JOIN product_variants v ON p.id = v.product_id "
                + "GROUP BY p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description;";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                String id = rs.getString("id");
                String product_name = rs.getString("product_name");
                String brand_id = rs.getString("brand_id");
                int category_id = rs.getInt("category_id");
                Boolean is_Active = rs.getBoolean("is_active");
                String description = rs.getString("description");
                Double display_price = rs.getDouble("display_price");
                Double display_max_price = rs.getDouble("display_max_price");
                String display_image = rs.getString("display_image");
                
            
                Product product = new Product(id, product_name, brand_id, category_id, is_Active, description, display_price, display_image);
                product.setDisplay_max_price(display_max_price);
                productList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return productList;
    }
    
    public Product getProductById(String id){
        String sql = "SELECT * FROM product WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"),
                    rs.getString("description"), null, null
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }   
        
        return null;
    }

    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO product (id, product_name, brand_id, category_id, description, is_active) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, product.getId());
            ps.setString(2, product.getProduct_name()); // Cập nhật thành getProduct_name
            ps.setString(3, product.getBrand_id());     // Cập nhật thành getBrand_id
            ps.setInt(4, product.getCategory_id());     // Cập nhật thành getCategory_id
            ps.setString(5, product.getDescription());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE product SET product_name = ?, brand_id = ?, category_id = ?, description = ?, is_active = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, product.getProduct_name());
            ps.setString(2, product.getBrand_id());
            ps.setInt(3, product.getCategory_id());
            ps.setString(4, product.getDescription());
            ps.setBoolean(5, product.getIs_active());
            ps.setString(6, product.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean softDeleteProduct(String id) {
        String sql = "UPDATE product SET is_active = 0 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean restoreProduct(String id) {
        String sql = "UPDATE product SET is_active = 1 WHERE id = ?";
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
