
package model;


public class Product {
    private String id;
    private String productName;
    private String brandId;
    private String categoryId;
    private int isActive;
    private String description;
    
    public Product() {}
    
    public Product(String id, String productName, String brandId, String categoryId, int isActive, String description){
        this.id = id;
        this.productName = productName;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.isActive = isActive;
        this.description = description;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id;}
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getBrandId() { return brandId; }
    public void setBrandId(String brandId) { this.brandId = brandId; }
    
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    
    public int getIsActive() { return isActive; }
    public void setIsActive(int isActive) { this.isActive = isActive; }
 
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
