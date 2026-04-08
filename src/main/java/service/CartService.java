package service;

import dao.CartDAO;
import model.ProductVariant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chinh
 */
public class CartService {
    
    private CartDAO cartDAO = new CartDAO();
    
    public String getCartFromUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        return cartDAO.getCartFromUser(userId);
    }
    
    public List<ProductVariant> getAllProductVariantFromCart(String cartId) {
        if (cartId == null || cartId.trim().isEmpty()) {
            return new ArrayList<>(); 
        }
        return cartDAO.getAllProductVariantFromCart(cartId);
    }
    
    public void addProductToCart(String cartId, ProductVariant productVariant, int amount) {
        if (cartId != null && !cartId.trim().isEmpty() && productVariant != null && amount > 0) {
            cartDAO.addProductToCart(cartId, productVariant, amount);
        } else {
            System.out.println("Dữ liệu đầu vào không hợp lệ. Không thể thêm vào giỏ hàng.");
        }
    }
    
    public void removeProductFromCart(String cartId, String productVariantId) {
        if (cartId != null && !cartId.trim().isEmpty() && productVariantId != null && !productVariantId.trim().isEmpty()) {
            cartDAO.removeProductFromCart(cartId, productVariantId);
        }
    }
}