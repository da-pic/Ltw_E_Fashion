package service;

import model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    /**
     * Thêm sản phẩm vào giỏ. Nếu đã có thì tăng số lượng lên 1.
     */
    public void addItem(List<CartItem> cart, String variantId) {
        for (CartItem item : cart) {
            if (item.getProductVariantId().equals(variantId)) {
                item.setAmount(item.getAmount() + 1);
                return;
            }
        }
        // Chưa có → tạo mới (thông tin đầy đủ nên được fetch từ DB ở tầng thực tế)
        CartItem item = new CartItem();
        item.setProductVariantId(variantId);
        item.setProductName("Sản phẩm");
        item.setAmount(1);
        item.setUnitPrice(100000);
        cart.add(item);
    }

    /** Tăng số lượng một sản phẩm lên 1. */
    public void increaseItem(List<CartItem> cart, String variantId) {
        for (CartItem item : cart) {
            if (item.getProductVariantId().equals(variantId)) {
                item.setAmount(item.getAmount() + 1);
                return;
            }
        }
    }

    /** Giảm số lượng một sản phẩm đi 1; xóa nếu về 0. */
    public void decreaseItem(List<CartItem> cart, String variantId) {
        cart.removeIf(item -> {
            if (item.getProductVariantId().equals(variantId)) {
                item.setAmount(item.getAmount() - 1);
                return item.getAmount() <= 0;
            }
            return false;
        });
    }

    /** Xóa hẳn một sản phẩm khỏi giỏ. */
    public void removeItem(List<CartItem> cart, String variantId) {
        cart.removeIf(item -> item.getProductVariantId().equals(variantId));
    }

    /** Tính subtotal, phí ship, tổng cộng. */
    public int calculateSubtotal(List<CartItem> cart) {
        return cart.stream().mapToInt(CartItem::getLineTotal).sum();
    }

    public int calculateShipping(int subtotal) {
        return subtotal >= 1_000_000 ? 0 : 30_000;
    }

    public int calculateTotal(int subtotal, int shipping) {
        return subtotal + shipping;
    }


    public List<CartItem> initCart(List<CartItem> cart) {
        return cart == null ? new ArrayList<>() : cart;
    }
}