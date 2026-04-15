package model;


public class CartItem {
    private String cart_id;
    private String product_variant_id;
    private int amount;
    private int unit_price;
    
    private String productName;
    private ProductVariant productVariant;

    public CartItem() {
    }

    public CartItem(String cart_id, String product_variant_id, int amount, int unit_price) {
        this.cart_id = cart_id;
        this.product_variant_id = product_variant_id;
        this.amount = amount;
        this.unit_price = unit_price;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_variant_id() {
        return product_variant_id;
    }

    public void setProduct_variant_id(String product_variant_id) {
        this.product_variant_id = product_variant_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public int getTotalPrice() {
        return this.amount * this.unit_price;
    }
    
}