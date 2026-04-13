package model;

public class CartItem {
    private String cartID;
    private String productVariantId;
    private String productName;
    private String image;
    private String color;
    private String size;
    private int amount;
    private int unitPrice;

    public CartItem() {}

    public CartItem(String cartID, String productVariantId, String productName, String image,
                       String color, String size, int amount, int unitPrice) {
        this.cartID = cartID;
        this.productVariantId = productVariantId;
        this.productName = productName;
        this.image = image;
        this.color = color;
        this.size = size;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public String getProductVariantId() { return productVariantId; }
    public void setProductVariantId(String productVariantId) { this.productVariantId = productVariantId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getUnitPrice() { return unitPrice; }
    public void setUnitPrice(int unitPrice) { this.unitPrice = unitPrice; }
    
    public String getCartID() { return this.cartID; }
    public void setCartID(String cartID) { this.cartID = cartID; }
}