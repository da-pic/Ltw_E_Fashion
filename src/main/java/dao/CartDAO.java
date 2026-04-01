
package dao;

import model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public String getCartIdByUserId(String userId) throws Exception{
        String sql = "SELECT id FROM cart WHERE user_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return rs.getString("id");
                }
            }
        }
        return null;
    }
    public List<CartItem> getCartItems(String cartId) throws Exception {
        List<CartItem> list = new ArrayList<>();
        
        String sql = "SELECT ci.cart_id, ci_product_variant_id, ci.amount, ci.unit_price, " +
                    "pv.stock, pv.color, pv.size, p.product_name " + 
                    "FROM cart_items ci " + 
                    "JOIN product_variants pv ON ci.product_variant_id = pv.id " +
                    "JOIN product p ON pv.product_id = p.id " + 
                    "WHERE ci.cart_id = ? ";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cartId);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    CartItem item = new CartItem();
                    item.setCartId(rs.getString("cart_id"));
                    item.setProductVariantId(rs.getString("product_variant_id"));
                    item.setAmount(rs.getInt("amount"));
                    item.setUnitPrice(rs.getInt("unit_price"));
                    item.setStock(rs.getInt("stock"));
                    item.setColor(rs.getString("color"));
                    item.setSize(rs.getString("size"));
                    item.setProductName(rs.getString("product_name"));
               list.add(item);
                }
            }
        }

        return list;
    }

    public void clearCartItems(Connection conn, String cartId) throws Exception {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cartId);
            ps.executeUpdate();
        }
    }
}