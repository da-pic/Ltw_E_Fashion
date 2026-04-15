package dao;

import model.ProductVariant;
import java.util.*;
import java.sql.*;
import util.DatabaseConnection;

public class ProductVariantDAO {
    
    //Lấy danh sách phân loại sản phẩm theo id
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
                String sku = rs.getString("sku");
                String color = rs.getString("color");
                String size = rs.getString("size");
                String image = rs.getString("image");
                int import_price = rs.getInt("import_price");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                boolean is_active = rs.getBoolean("is_active");
                
                ProductVariant variant = new ProductVariant(id, product_id, sku, color, size, image, import_price, price, stock, is_active);
                productVariantsList.add(variant);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return productVariantsList;
    }
    
    //Lấy id sản phẩm bằng id phân loại
    public String getProductIdByVariantId(String variantId) {
        String productId = null;
        String sql = "SELECT product_id FROM product_variants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, variantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                productId = rs.getString("product_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productId;
    }
    
    //Lấy giá bán theo id phân loại
    public int getPriceByVariantId(String variantId) {
        int price = 0;
        String sql = "SELECT price FROM product_variants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, variantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) price = rs.getInt("price");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
    
    //Lấy thông tin phân loại
    public ProductVariant getProductVariantById(String variantId) {
        String sql = "SELECT * FROM product_variants WHERE id = ?";
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, variantId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getString("id"));
                variant.setProduct_id(rs.getString("product_id"));
                variant.setSku(rs.getString("sku"));
                variant.setColor(rs.getString("color"));
                variant.setSize(rs.getString("size"));
                variant.setImage(rs.getString("image"));
                variant.setImport_price(rs.getInt("import_price"));
                variant.setPrice(rs.getInt("price"));
                variant.setStock(rs.getInt("stock"));
                variant.setIs_active(rs.getBoolean("is_active"));
                
                return variant;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //Lấy tên sản phẩm bằng id phân loại
    public String getProductNameByVariantId(String variantId) {
        String productName = null;
        String sql = "SELECT p.product_name "
                   + "FROM product p "
                   + "JOIN product_variants pv ON p.id = pv.product_id "
                   + "WHERE pv.id = ?";
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, variantId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                productName = rs.getString("product_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productName;
    }
}