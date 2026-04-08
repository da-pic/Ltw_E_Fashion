/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.ProductVariant;
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
    
    public List<ProductVariant> getAllProductVariantFromCart(String cartID){
        String sql = "SELECT pv.* " +
                 "FROM cart_items ci " +
                 "JOIN product_variants pv ON ci.product_variant_id = pv.id " +
                 "WHERE ci.cart_id = ?";
    
        List<ProductVariant> list = new ArrayList<>();
    
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
        
            stmt.setString(1, cartID);
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

                ProductVariant pv = new ProductVariant(
                    id, product_id, color, size, image, price, stock, is_active
                );
            
                list.add(pv);
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
    
        return list;
    }
    
    public void addProductToCart(String cartID, ProductVariant product_variant, int amount){
        String checkSql = "SELECT amount FROM cart_items WHERE cart_id = ? AND product_variant_id = ?";
        String updateSql = "UPDATE cart_items SET amount = amount + ? WHERE cart_id = ? AND product_variant_id = ?";
        String insertSql = "INSERT INTO cart_items (cart_id, product_variant_id, amount, unit_price) VALUES (?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection()){

            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, cartID);
            checkStmt.setString(2, product_variant.getId());
            ResultSet rs = checkStmt.executeQuery();

            if(rs.next()){
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, amount);
                updateStmt.setString(2, cartID);
                updateStmt.setString(3, product_variant.getId());
                updateStmt.executeUpdate();

            } else {
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, cartID);
                insertStmt.setString(2, product_variant.getId());
                insertStmt.setInt(3, amount);
                insertStmt.setInt(4, product_variant.getPrice());
                insertStmt.executeUpdate();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    } 
    
    public void removeProductFromCart(String cartID, String productVariantId){
        String sql = "DELETE FROM cart_items WHERE product_variant_id = ? AND cart_id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, productVariantId);
            stmt.setString(2, cartID);
            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}