package dao;

import java.sql.*;
import util.DatabaseConnection;
import model.Product;
import java.util.*;

public class ProductDAO {
    
    //Số lượng đã bấn của sản phẩm
    private final String COUNT_SOLD_SQL = "COALESCE((SELECT SUM(oi.amount) FROM order_items oi "
                                        + "JOIN orders o ON oi.order_id = o.id "
                                        + "JOIN product_variants pv_sold ON oi.product_variant_id = pv_sold.id "
                                        + "WHERE pv_sold.product_id = p.id AND o.order_status = 'delivered'), 0) AS sold_count ";

    //Lấy tất cả sản phẩm
    public List<Product> getAllProduct(){
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description, "
                + "MIN(v.price) AS display_price, "
                + "(SELECT image FROM product_variants WHERE product_id = p.id LIMIT 1) AS display_image, "
                + COUNT_SOLD_SQL 
                + "FROM product p "
                + "LEFT JOIN product_variants v ON p.id = v.product_id "
                + "GROUP BY p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description;";
                
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Product product = new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"), rs.getString("description"),
                    rs.getDouble("display_price"), rs.getString("display_image")
                );
                product.setSold_count(rs.getInt("sold_count"));
                productList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productList;
    }
    
    
    //Lấy sản phaamr theo id
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
    
    //Lấy sản phẩm đề xuất
    public List<Product> getAllProductsSortedByPreference(String userId) {
        List<Product> productList = new ArrayList<>();
        if (userId == null || userId.trim().isEmpty()) { userId = "GUEST_USER_NO_ID"; }

        String sql = "SELECT p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description, "
                + "MIN(pv.price) AS display_price, "
                + "(SELECT image FROM product_variants WHERE product_id = p.id LIMIT 1) AS display_image, "
                + COUNT_SOLD_SQL + ", " // <--- Bổ sung đếm lượt bán
                + "COALESCE(us.total_score, 0) AS final_score, "
                + "COALESCE(cat_scores.cat_max_score, 0) AS category_priority "
                + "FROM product p "
                + "LEFT JOIN product_variants pv ON p.id = pv.product_id "
                
                + "LEFT JOIN ( "
                + "    SELECT product_id, SUM(score) AS total_score FROM ( "
                + "        SELECT pv.product_id, 4 AS score FROM orders o JOIN order_items oi ON o.id = oi.order_id JOIN product_variants pv ON oi.product_variant_id = pv.id WHERE o.user_id = ? "
                + "        UNION ALL "
                + "        SELECT pv.product_id, 3 AS score FROM cart c JOIN cart_items ci ON c.id = ci.cart_id JOIN product_variants pv ON ci.product_variant_id = pv.id WHERE c.user_id = ? "
                + "        UNION ALL "
                + "        SELECT product_id, CASE WHEN interaction_type = 'SEARCH' THEN 2 ELSE 1 END AS score FROM user_interactions WHERE user_id = ? "
                + "    ) AS temp GROUP BY product_id "
                + ") AS us ON p.id = us.product_id "
                
                + "LEFT JOIN ( "
                + "    SELECT p_inner.category_id, MAX(us_inner.total_score) AS cat_max_score FROM product p_inner JOIN ( "
                + "        SELECT product_id, SUM(score) AS total_score FROM ( "
                + "            SELECT pv.product_id, 4 AS score FROM orders o JOIN order_items oi ON o.id = oi.order_id JOIN product_variants pv ON oi.product_variant_id = pv.id WHERE o.user_id = ? AND o.order_status = 'delivered' "
                + "            UNION ALL "
                + "            SELECT pv.product_id, 3 AS score FROM cart c JOIN cart_items ci ON c.id = ci.cart_id JOIN product_variants pv ON ci.product_variant_id = pv.id WHERE c.user_id = ? "
                + "            UNION ALL "
                + "            SELECT product_id, CASE WHEN interaction_type = 'SEARCH' THEN 2 ELSE 1 END AS score FROM user_interactions WHERE user_id = ? "
                + "        ) AS temp_inner GROUP BY product_id "
                + "    ) AS us_inner ON p_inner.id = us_inner.product_id GROUP BY p_inner.category_id "
                + ") AS cat_scores ON p.category_id = cat_scores.category_id "
                
                + "WHERE p.is_active = TRUE "
                + "GROUP BY p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description, final_score, category_priority "
                + "ORDER BY category_priority DESC, final_score DESC, p.id DESC;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId); stmt.setString(2, userId); stmt.setString(3, userId);
            stmt.setString(4, userId); stmt.setString(5, userId); stmt.setString(6, userId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"), rs.getString("description"),
                    rs.getDouble("display_price"), rs.getString("display_image")
                );
                product.setSold_count(rs.getInt("sold_count"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    
    //Lưu thông tin cho log view
    public void logUserInteraction(String userId, String productId, String type) {
        String sql = "INSERT INTO user_interactions (user_id, product_id, interaction_type) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, productId);
            stmt.setString(3, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Tìm kiếm sản phẩm 
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String exactOrStartsWith = keyword + "%";
        String contains = "%" + keyword + "%";
        
        String sql = "SELECT p.*, "
                   + "(SELECT MIN(price) FROM product_variants pv WHERE pv.product_id = p.id) as display_price, "
                   + "(SELECT image FROM product_variants pv WHERE pv.product_id = p.id LIMIT 1) as display_image, "
                   + COUNT_SOLD_SQL 
                   + "FROM product p "
                   + "LEFT JOIN category c ON p.category_id = c.id "
                   + "WHERE p.is_active = 1 AND (p.product_name LIKE ? OR c.name LIKE ?) "
                   + "ORDER BY "
                   + "  CASE "
                   + "    WHEN p.product_name LIKE ? THEN 3 "
                   + "    WHEN p.product_name LIKE ? THEN 2 "
                   + "    WHEN c.name LIKE ? THEN 1 "
                   + "    ELSE 0 "
                   + "  END DESC, p.product_name ASC";
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, contains); ps.setString(2, contains);
            ps.setString(3, exactOrStartsWith); ps.setString(4, contains); ps.setString(5, contains);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"), rs.getString("description"),
                    rs.getDouble("display_price"), rs.getString("display_image")
                );
                product.setSold_count(rs.getInt("sold_count"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, "
                   + "(SELECT MIN(price) FROM product_variants pv WHERE pv.product_id = p.id) as display_price, "
                   + "(SELECT image FROM product_variants pv WHERE pv.product_id = p.id LIMIT 1) as display_image, "
                   + COUNT_SOLD_SQL
                   + "FROM product p "
                   + "WHERE p.category_id = ? AND p.is_active = 1";
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"), rs.getString("description"),
                    rs.getDouble("display_price"), rs.getString("display_image")
                );
                product.setSold_count(rs.getInt("sold_count"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Ghi log cho search
    public void logSearchInteractionsBatch(String userId, List<Product> products) {
        if (products == null || products.isEmpty()) return;
        
        String sql = "INSERT INTO user_interactions (user_id, product_id, interaction_type) VALUES (?, ?, 'SEARCH')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Product p : products) {
                stmt.setString(1, userId);
                stmt.setString(2, p.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Lấy Top 5 sản phẩm đc tìm kiếm nhiều nhất
    public List<Product> getTopSearchedProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, "
                   + "(SELECT MIN(price) FROM product_variants pv WHERE pv.product_id = p.id) as display_price, "
                   + "(SELECT image FROM product_variants pv WHERE pv.product_id = p.id LIMIT 1) as display_image, "
                   + COUNT_SOLD_SQL + ", " 
                   + "(SELECT COUNT(*) FROM user_interactions ui WHERE ui.product_id = p.id AND ui.interaction_type = 'SEARCH') AS search_count "
                   + "FROM product p "
                   + "WHERE p.is_active = 1 "
                   + "HAVING search_count > 0 "
                   + "ORDER BY search_count DESC "
                   + "LIMIT 5"; 
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"), rs.getString("description"),
                    rs.getDouble("display_price"), rs.getString("display_image")
                );
                product.setSold_count(rs.getInt("sold_count"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Lấy 5 sản phẩm áp đc voucher với % lớn nhất
    public List<Product> getFlashSaleProducts() {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.*, "
                   + "(SELECT MIN(price) FROM product_variants pv WHERE pv.product_id = p.id) as display_price, "
                   + "(SELECT image FROM product_variants pv WHERE pv.product_id = p.id LIMIT 1) as display_image, "
                   + COUNT_SOLD_SQL + ", " 
                   + "(SELECT MAX(c.value) FROM coupon c "
                   + " WHERE c.is_active = 1 "
                   + " AND (c.type = 'percent' OR c.type = '%') " 
                   + " AND c.expired_date >= CURDATE() " 
                   + " AND c.min_cost <= (SELECT MIN(price) FROM product_variants pv WHERE pv.product_id = p.id) " 
                   + ") AS max_discount "
                   + "FROM product p "
                   + "WHERE p.is_active = 1 "
                   + "HAVING max_discount > 0 " // CỰC KỲ QUAN TRỌNG: Chỉ lấy những sản phẩm CÓ THỂ áp được mã!
                   + "ORDER BY RAND() LIMIT 5"; 
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getString("id"), rs.getString("product_name"), rs.getString("brand_id"),
                    rs.getInt("category_id"), rs.getBoolean("is_active"), rs.getString("description"),
                    rs.getDouble("display_price"), rs.getString("display_image")
                );
                product.setSold_count(rs.getInt("sold_count"));
                product.setMax_discount(rs.getInt("max_discount")); 
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}