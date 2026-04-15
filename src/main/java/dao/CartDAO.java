package dao;

import java.sql.*;
import java.util.*;
import model.Cart;
import model.CartItem;
import model.ProductVariant;
import util.DatabaseConnection;

public class CartDAO {

    public String getOrCreateCartId(String userId) {
        String cartId = null;
        String checkSql = "SELECT id FROM cart WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            psCheck.setString(1, userId);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                cartId = rs.getString("id");
            } else {
                cartId = UUID.randomUUID().toString();
                String insertSql = "INSERT INTO cart (id, user_id) VALUES (?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setString(1, cartId);
                    psInsert.setString(2, userId);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartId;
    }

    public Cart getCartByUserId(String userId) {
        String cartId = getOrCreateCartId(userId);
        Cart cart = new Cart(cartId, userId);
        
        String sql = "SELECT ci.cart_id, ci.product_variant_id, ci.amount, ci.unit_price, "
                   + "pv.color, pv.size, pv.image, pv.stock "
                   + "FROM cart_items ci "
                   + "JOIN product_variants pv ON ci.product_variant_id = pv.id "
                   + "WHERE ci.cart_id = ?";
                   
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCart_id(rs.getString("cart_id"));
                item.setProduct_variant_id(rs.getString("product_variant_id"));
                item.setAmount(rs.getInt("amount"));
                item.setUnit_price(rs.getInt("unit_price"));
                
                ProductVariant variant = new ProductVariant(
                    rs.getString("product_variant_id"), null, rs.getString("color"), 
                    rs.getString("size"), rs.getString("image"), rs.getInt("unit_price"), 
                    rs.getInt("stock"), true
                );
                item.setProductVariant(variant);
                
                cart.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    public void upsertCartItem(String cartId, String variantId, int quantity, int unitPrice) {
        String checkSql = "SELECT amount FROM cart_items WHERE cart_id = ? AND product_variant_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            
            psCheck.setString(1, cartId);
            psCheck.setString(2, variantId);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                int currentAmount = rs.getInt("amount");
                String updateSql = "UPDATE cart_items SET amount = ? WHERE cart_id = ? AND product_variant_id = ?";
                try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                    psUpdate.setInt(1, currentAmount + quantity);
                    psUpdate.setString(2, cartId);
                    psUpdate.setString(3, variantId);
                    psUpdate.executeUpdate();
                }
            } else {
                String insertSql = "INSERT INTO cart_items (cart_id, product_variant_id, amount, unit_price) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setString(1, cartId);
                    psInsert.setString(2, variantId);
                    psInsert.setInt(3, quantity);
                    psInsert.setInt(4, unitPrice);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCartItem(String cartId, String variantId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_variant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.setString(2, variantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getCartItemQuantity(String cartId, String variantId) {
        int quantity = 0;
        String sql = "SELECT amount FROM cart_items WHERE cart_id = ? AND product_variant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cartId);
            ps.setString(2, variantId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                quantity = rs.getInt("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    public void updateCartItemQuantity(String cartId, String variantId, int newQuantity) {
        String sql = "UPDATE cart_items SET amount = ? WHERE cart_id = ? AND product_variant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, newQuantity);
            ps.setString(2, cartId);
            ps.setString(3, variantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}