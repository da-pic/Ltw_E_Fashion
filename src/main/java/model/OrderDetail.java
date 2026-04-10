package model;

public class OrderDetail {
    private int id;
    private String orderId;
    private String variantId;
    private int quantity;
    private int price; 

    private String productName;
    private String color;
    private String size;
    private String image;

    public OrderDetail() {}

    public OrderDetail(int id, String orderId, String variantId, int quantity, int price) {
        this.id = id;
        this.orderId = orderId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getVariantId() { return variantId; }
    public void setVariantId(String variantId) { this.variantId = variantId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}