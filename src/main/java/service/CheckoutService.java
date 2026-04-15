package service;

import dao.CartDAO;
import dao.AddressDAO;
import dao.OrderDAO;
import dao.ProductVariantDAO;
import model.CartItem;
import model.ProductVariant;
import model.Address;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    private AddressDAO addressDAO = new AddressDAO();
    private CartDAO cartDAO = new CartDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private CouponService couponService = new CouponService();

    public List<Address> getUserAddresses(String userId) {
        return addressDAO.getAddressByUserId(userId);
    }

    public List<CartItem> getItemsForCheckoutFromCart(String userId, String[] selectedVariantIds) {
        List<CartItem> checkoutItems = new ArrayList<>();
        if (userId == null || selectedVariantIds == null || selectedVariantIds.length == 0) {
            return checkoutItems;
        }

        String cartId = cartDAO.getOrCreateCartId(userId);
        
        for (String variantId : selectedVariantIds) {
            int quantity = cartDAO.getCartItemQuantity(cartId, variantId);
            if (quantity > 0) {
                ProductVariant variant = variantDAO.getProductVariantsById(variantId);
                if (variant != null) {
                    CartItem item = new CartItem();
                    item.setProductVariantId(variantId);
                    item.setAmount(quantity);
                    item.setUnitPrice(variant.getPrice()); 
                    item.setProductVariant(variant);
                    checkoutItems.add(item);
                }
            }
        }
        return checkoutItems;
    }

    public List<CartItem> getItemsForCheckoutDirectly(String variantId, int quantity) {
        List<CartItem> checkoutItems = new ArrayList<>();
        if (variantId == null || quantity <= 0) return checkoutItems;

        ProductVariant variant = variantDAO.getProductVariantsById(variantId);
        if (variant != null) {
            CartItem item = new CartItem();
            item.setProductVariantId(variantId);
            item.setAmount(quantity);
            item.setUnitPrice(variant.getPrice());
            item.setProductVariant(variant);
            checkoutItems.add(item);
        }
        return checkoutItems;
    }

    public int calculateCheckoutTotal(List<CartItem> items) {
        int total = 0;
        if (items != null) {
            for (CartItem item : items) {
                total += item.getTotalPrice(); 
            }
        }
        return total;
    }

    public boolean processOrder(String userId, String cartId, int addressId, String paymentMethod, String couponId, String[] variantIds, String[] quantities) {
        if (variantIds == null || variantIds.length == 0) return false;
        
        int[] unitPrices = new int[variantIds.length];
        int grandTotal = 0;
        
        for (int i = 0; i < variantIds.length; i++) {
            int price = variantDAO.getPriceByVariantId(variantIds[i]);
            unitPrices[i] = price;
            grandTotal += price * Integer.parseInt(quantities[i]);
        }

        if (couponId != null && !couponId.trim().isEmpty()) {
            int discount = couponService.calculateDiscountAmount(userId, couponId, grandTotal);
            if (discount > 0) {
                grandTotal -= discount; 
            } else {
                couponId = null; 
            }
        }
        
        String orderId = java.util.UUID.randomUUID().toString();
 
        boolean isSuccess = orderDAO.placeOrder(orderId, userId, grandTotal, paymentMethod, addressId, couponId, variantIds, quantities, unitPrices);
        
        if (isSuccess && cartId != null) {
            for (String vid : variantIds) {
                cartDAO.removeCartItem(cartId, vid);
            }
        }
        
        return isSuccess;
    }
}