package model;

import java.util.List;

public class Product {
    private String id;
    private String product_name;
    private String brand_id;
    private String supplier_id;
    private int category_id;
    private boolean is_active;
    private String description;
    private String display_image; 
    private int display_price;
    private Double display_max_price;
    private int sold_count;
    private int max_discount;
    
    
    private String brandName;
    private String category;

    private List<ProductVariant> productVariants;

    // ===== Constructor =====
    public Product(String id, String product_name, String brand_id, String supplier_id, int category_id, 
            boolean is_active, String description, int display_price, String display_image) {
        this.id = id;
        this.product_name = product_name;
        this.brand_id = brand_id;
        this.category_id = category_id;
        this.is_active = is_active;
        this.description = description;
        this.display_price = display_price; 
        this.display_image = display_image;
        this.supplier_id = supplier_id;
    }

    public Product() {}

    // ===== Getter =====
    public String getId() { return id; }
    public String getProduct_name() { return product_name; }
    public String getBrand_id() { return brand_id; }
    public int getCategory_id() { return category_id; }
    public boolean isActive() { return is_active; }
    public String getDescription() { return description; }
    public String getDisplay_image() { return display_image; }
    public int getDisplay_price() { return display_price; }
    public String getSupplierID() { return supplier_id; }
    public List<ProductVariant> getProductVariants() { return productVariants; }
    public String getBrandName() { return brandName; }
    public String getCategory() { return category; }

    // ===== Setter =====
    public void setId(String id) { this.id = id; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }
    public void setBrand_id(String brand_id) { this.brand_id = brand_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }
    public void setIs_active(boolean is_active) { this.is_active = is_active; }
    public void setDescription(String description) { this.description = description; }
    public void setDisplay_image(String display_image) { this.display_image = display_image; }
    public void setDisplay_price(int display_price) { this.display_price = display_price; }
    public void setSupplierID(String supplier_id) { this.supplier_id = supplier_id; }
    public void setProductVariants(List<ProductVariant> productVariants) { this.productVariants = productVariants; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public void setCategory(String category) { this.category = category; }
    public void setDisplay_max_price(Double display_max_price) {
        this.display_max_price = display_max_price;
    }
    
    public Double getDisplay_max_price() {
        return display_max_price;
    }
    
    public int getSold_count() {
        return sold_count;
    }

    public void setSold_count(int sold_count) {
        this.sold_count = sold_count;
    }
    
    public int getMax_discount() {
        return max_discount;
    }
    
    public void setMax_discount(int max_discount) {
        this.max_discount = max_discount;
    }
}