
package dao;

import model.CartItem;
import java.util.*;
import java.sql.*;
import util.*;
public class CartDAO {
    public String getCartFromUser(String user_id){
        String sql = "SELECT id FROM cart WHERE user_id = ?";
    
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
        
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();
        
            if(rs.next()){
                return rs.getString("id");
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }
    
    public boolean createUserCart(String userID){
        String sql = "INSERT INTO cart (id, user_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {

            String cartId = UUID.randomUUID().toString();

            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cartId);
            stmt.setString(2, userID);
            return stmt.executeUpdate() > 0;
        
        } catch (SQLException e){
            e.printStackTrace();
        }
   
        return false;
    }
    
    public List<CartItem> getCartItemsByID(String cartID) {

        List<CartItem> list = new ArrayList<>();

        String sql = "SELECT ci.cart_id, ci.product_variant_id, ci.amount, ci.unit_price, " +
                     "p.product_name, pv.image, pv.color, pv.size " +
                     "FROM cart c " +
                     "JOIN cart_items ci ON c.id = ci.cart_id " +
                     "JOIN product_variants pv ON ci.product_variant_id = pv.id " +
                     "JOIN product p ON pv.product_id = p.id " +
                     "WHERE c.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cartID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                    rs.getString("cart_id"),
                    rs.getString("product_variant_id"),
                    rs.getString("product_name"),
                    rs.getString("image"),
                    rs.getString("color"),
                    rs.getString("size"),
                    rs.getInt("amount"),
                    rs.getInt("unit_price")
                );

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean addItemsIntoCart(String userID, String productVariantID, int amount, int unitPrice) {

        String cartId = getCartFromUser(userID);

        try (Connection conn = DatabaseConnection.getConnection()) {

            String insertSQL = "INSERT INTO cart_items (cart_id, product_variant_id, "
                    + "amount, unit_price) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSQL);

            insertStmt.setString(1, cartId);
            insertStmt.setString(2, productVariantID);
            insertStmt.setInt(3, amount);
            insertStmt.setInt(4, unitPrice);

            return insertStmt.executeUpdate() > 0;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public CartItem getItemFromCart(String cartId, String productVariantID) {

        String sql = "SELECT ci.cart_id, ci.product_variant_id, ci.amount, ci.unit_price, " +
                     "p.product_name, pv.image, pv.color, pv.size " +
                     "FROM cart_items ci " +
                     "JOIN product_variants pv ON ci.product_variant_id = pv.id " +
                     "JOIN product p ON pv.product_id = p.id " +
                     "WHERE ci.cart_id = ? AND ci.product_variant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cartId);
            stmt.setString(2, productVariantID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CartItem(
                    rs.getString("cart_id"),
                    rs.getString("product_variant_id"),
                    rs.getString("product_name"),
                    rs.getString("image"),
                    rs.getString("color"),
                    rs.getString("size"),
                    rs.getInt("amount"),
                    rs.getInt("unit_price")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean updateCartItem(String userID, String productVariantID, int amount){
        String cartID = getCartFromUser(userID);
        String insertSQL = "UPDATE cart_items SET amount = amount + ? "
                    + "WHERE cart_id = ? AND product_variant_id = ?";
        
        try(Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            
            stmt.setInt(1, amount);
            stmt.setString(2, cartID);
            stmt.setString(3, productVariantID);
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public boolean deleteCartItem(String cartId, String productVariantID) {

        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_variant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cartId);
            stmt.setString(2, productVariantID);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
