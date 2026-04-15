package model;

import java.util.List;

/**
 *
 * @author Chinh
 */
public class Product {
    private String id;
    private String product_name;
    private String brand_id;
    private int category_id;
    private Boolean is_active;
    private String supplier_id; 
    private String description;
    private String display_image; 
    private Double display_price;
    private int sold_count;
    private int max_discount;
    private List<ProductVariant> productVariants;

    public Product() {
    }

    public Product(String id, String product_name, String brand_id, int category_id, Boolean is_active, String supplier_id, String description) {
        this.id = id;
        this.product_name = product_name;
        this.brand_id = brand_id;
        this.category_id = category_id;
        this.is_active = is_active;
        this.supplier_id = supplier_id;
        this.description = description;
    }

    public Product(String id, String product_name, String brand_id, int category_id, Boolean is_active, String description, Double display_price, String display_image) {
        this.id = id;
        this.product_name = product_name;
        this.brand_id = brand_id;
        this.category_id = category_id;
        this.is_active = is_active;
        this.description = description;
        this.display_price = display_price; 
        this.display_image = display_image;
    }

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public String getDescription() {
        return description;
    }
    
    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }
    
    public String getDisplay_image() {
        return display_image;
    }

    public Double getDisplay_price() {
        return display_price;
    }

    public int getMax_discount() {
        return max_discount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplay_image(String display_image) {
        this.display_image = display_image;
    }

    public void setDisplay_price(Double display_price) {
        this.display_price = display_price;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public int getSold_count() {
        return sold_count;
    }

    public void setSold_count(int sold_count) {
        this.sold_count = sold_count;
    }

    public void setMax_discount(int max_discount) {
        this.max_discount = max_discount;
    }

}