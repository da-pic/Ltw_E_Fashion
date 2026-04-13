
package dao;
import java.sql.*;
import util.DatabaseConnection;
import model.Product;
import java.util.*;

public class ProductDAO {
    public List<Product> getAllProduct() {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.id, p.product_name, p.brand_id, p.category_id, " +
                     "p.is_active, p.description, p.supplier_id, " +
                     "MIN(v.price) AS display_price, " +
                     "(SELECT image FROM product_variants WHERE product_id = p.id LIMIT 1) AS display_image " +
                     "FROM product p " +
                     "LEFT JOIN product_variants v ON p.id = v.product_id " +
                     "GROUP BY p.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Product p = new Product(
                    rs.getString("id"),
                    rs.getString("product_name"),
                    rs.getString("brand_id"),
                    rs.getString("supplier_id"),
                    rs.getInt("category_id"),
                    rs.getBoolean("is_active"),
                    rs.getString("description"),
                    rs.getInt("display_price"),
                    rs.getString("display_image")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public Product getProductById(String id) {

        String sql = "SELECT p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description, " +
                     "s.supplier_name, " +
                     "MIN(v.price) AS display_price, " +
                     "(SELECT image FROM product_variants WHERE product_id = p.id LIMIT 1) AS display_image " +
                     "FROM product p " +
                     "LEFT JOIN product_variants v ON p.id = v.product_id " +
                     "LEFT JOIN supplier s ON p.supplier_id = s.id " +
                     "WHERE p.id = ? " +
                     "GROUP BY p.id, p.product_name, p.brand_id, p.category_id, p.is_active, p.description, s.supplier_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                    rs.getString("id"),
                    rs.getString("product_name"),
                    rs.getString("brand_id"),
                    rs.getString("supplier_name"),
                    rs.getInt("category_id"),
                    rs.getBoolean("is_active"),
                    rs.getString("description"),
                    rs.getInt("display_price"),
                    rs.getString("display_image")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean addProduct(Product p) {

        String sql = "INSERT INTO product (id, product_name, brand_id, category_id, "
                     + "supplier_id, is_active, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getId());
            stmt.setString(2, p.getProduct_name());
            stmt.setString(3, p.getBrand_id());
            stmt.setInt(4, p.getCategory_id());

            stmt.setString(5, p.getSupplierID());

            stmt.setBoolean(6, p.isActive());
            stmt.setString(7, p.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean updateProduct(Product p) {

        String sql = "UPDATE product SET product_name = ?, brand_id = ?, category_id = ?, " +
                     "supplier_id = ?, is_active = ?, description = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getProduct_name());
            stmt.setString(2, p.getBrand_id());
            stmt.setInt(3, p.getCategory_id());
            stmt.setString(4, p.getSupplierID());
            stmt.setBoolean(5, p.isActive());
            stmt.setString(6, p.getDescription());
            stmt.setString(7, p.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteProduct(String productId) {

        String sql = "UPDATE product SET is_active = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
