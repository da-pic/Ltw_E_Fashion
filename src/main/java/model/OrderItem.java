package model;

/**
 *
 * @author Chinh
 */
public class OrderItem {
    private String order_id;
    private String product_variant_id;
    private int amount;
    private int unit_price;
     
    private String productName;
    private String color;
    private String size;
    private String image;
    private ProductVariant productVariant;

    public OrderItem() {}

    public OrderItem(String order_id, String product_variant_id, int amount, int unit_price) {
        this.order_id = order_id;
        this.product_variant_id = product_variant_id;
        this.amount = amount;
        this.unit_price = unit_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public int getTotalPrice() {
        return this.amount * this.unit_price;
    }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}