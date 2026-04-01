
package dao;

import model.Product;
import java.sql.*;
import  java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/e_fashion";
    private static final String USER = "root";
    private static final String PASS = "123456";
    
    private Connection getConnection() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            throw new SQLException("MySQL Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
}
    public List<Product> getAllProducts(){
        List<Product>list = new ArrayList<>();
        String sql = "SELECT id, product_name, brand_id, category_id, is_active, description FROM product";
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public Product getProductById(String id){
        String sql = "SELECT id, product_name, brand_id, category_id, is_active, description FROM product WHERE id = ?";
        try(Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return mapRow(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean  addProduct (Product p) {
        String sql = "INSERT INTO product (id, product_name, brand_id, category_id, is_active, description) VALUES (?, ?, ?, ?, ?, ?)";
      try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
           ps.setString(1, p.getId());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getBrandId());
            ps.setString(4, p.getCategoryId());
            ps.setInt(5, p.getIsActive());
            ps.setString(6, p.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     public boolean updateProduct(Product p) {
        String sql = "UPDATE product SET product_name=?, brand_id=?, category_id=?, is_active=?, description=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setString(2, p.getBrandId());
            ps.setString(3, p.getCategoryId());
            ps.setInt(4, p.getIsActive());
            ps.setString(5, p.getDescription());
            ps.setString(6, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public boolean deleteProduct(String id) {
        String sql = "UPDATE product SET is_active = 0 WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
            rs.getString("id"),
            rs.getString("product_name"),
            rs.getString("brand_id"),
            rs.getString("category_id"),
            rs.getInt("is_active"),
            rs.getString("description")
        );
    }
}
    
