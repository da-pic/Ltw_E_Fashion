package service;

import dao.CartDAO;
import dao.ProductVariantDAO;
import model.Cart;

public class CartService {
    private CartDAO cartDAO = new CartDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();

    public Cart getCartForUser(String userId) {
        if (userId == null) return null;
        
        Cart cart = cartDAO.getCartByUserId(userId);
        
        if (cart != null && cart.getItems() != null) {
            for (model.CartItem item : cart.getItems()) {
                String name = variantDAO.getProductNameByVariantId(item.getProduct_variant_id());

                item.setProductName(name);
            }
        }
        
        return cart;
    }

    public void addToCart(String userId, String variantId, int quantity) {
        if (userId == null || variantId == null || quantity <= 0) return;

        String cartId = cartDAO.getOrCreateCartId(userId);

        int unitPrice = variantDAO.getPriceByVariantId(variantId); 

        cartDAO.upsertCartItem(cartId, variantId, quantity, unitPrice);
    }

    public void removeItem(String userId, String variantId) {
        String cartId = cartDAO.getOrCreateCartId(userId);
        cartDAO.removeCartItem(cartId, variantId);
    }
    
    public void decreaseItem(String userId, String variantId) {
        String cartId = cartDAO.getOrCreateCartId(userId);

        int currentQuantity = cartDAO.getCartItemQuantity(cartId, variantId);

        if (currentQuantity > 1) {
            cartDAO.updateCartItemQuantity(cartId, variantId, currentQuantity - 1);
        } else if (currentQuantity == 1) {
            cartDAO.removeCartItem(cartId, variantId);
        }
    }
}
    