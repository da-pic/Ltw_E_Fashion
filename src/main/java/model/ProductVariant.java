package model;

/**
 *
 * @author Chinh
 */
public class ProductVariant {
    private String id;
    private String product_id;
    private String sku;
    private String color;
    private String size;
    private String image;
    private int import_price;
    private int price;
    private int stock;
    private boolean is_active;

    public ProductVariant() {
    }

    public ProductVariant(String id, String product_id, String sku, String color, String size, 
                          String image, int import_price, int price, int stock, boolean is_active) {
        this.id = id;
        this.product_id = product_id;
        this.sku = sku;
        this.color = color;
        this.size = size;
        this.image = image;
        this.import_price = import_price;
        this.price = price;
        this.stock = stock;
        this.is_active = is_active;
    }

    public ProductVariant(String id, String product_id, String color, String size, 
                          String image, int price, int stock, boolean is_active) {
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

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImport_price() {
        return import_price;
    }

    public void setImport_price(int import_price) {
        this.import_price = import_price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}