package model;

public class ImportItem {
    private String importID;
    private String productVariantID;
    private String productName;
    private String supplierID;
    private String image;
    private String size;
    private String color;
    private int unitPrice;
    private int amount;

    public ImportItem() {}

    public ImportItem(String importID, String productVariantID, String productName,
                      String supplierID, String image, String size,
                      String color, int unitPrice, int amount) {

        this.importID = importID;
        this.productVariantID = productVariantID;
        this.productName = productName;
        this.supplierID = supplierID;
        this.image = image;
        this.size = size;
        this.color = color;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public String getImportID() {
        return importID;
    }

    public void setImportID(String importID) {
        this.importID = importID;
    }

    public String getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(String productVariantID) {
        this.productVariantID = productVariantID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}