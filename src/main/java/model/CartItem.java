package model;

public class CartItem {

    private String productVariantId; // ✅ chỉ giữ cái này
    private String productName;
    private int amount;
    private int unitPrice;
    private int stock;

    public CartItem() {}

    public CartItem(String productVariantId, String productName, int amount, int unitPrice, int stock) {
        this.productVariantId = productVariantId;
        this.productName = productName;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getLineTotal() {
        return amount * unitPrice;
    }
}