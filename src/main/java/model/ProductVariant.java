package model;

public class ProductVariant {
    private String id;
    private String product_id;
    private String color;
    private String size;
    private String image;
    private String importPrice;
    private int price;
    private int stock;
    private Boolean is_active;

    public ProductVariant(String id, String product_id, String color, String size, 
                          String image, int price, int stock, Boolean is_active) {
        this.id = id;
        this.product_id = product_id;
        this.color = color;
        this.size = size;
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.is_active = is_active;
    }

    public String getId() {
        return id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public void setImportPrice(int import_price) {
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
    
    
}
