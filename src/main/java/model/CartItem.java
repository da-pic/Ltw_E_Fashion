package model;

public class CartItem {
    private String cartId;
    private String productVariantId;
    private int amount;
    private int unitPrice;
    
    private int stock;
    private String productName;
    private String color;
    private String size;
    
    public CartItem(){
    }
    
    public String getCartId(){
        return cartId;
    }
    public void setCartId(String cartId){
        this.cartId = cartId;
    }
    
    public String getProductVariantId(){
        return productVariantId;
    }
    public void setProductVariantId(String productVariantId){
        this.productVariantId = productVariantId;
    }
    
    public int getAmount(){
        return amount;
    }
    public void setAmount(int amount){
       this.amount = amount;
}
    
    public int getUnitPrice(){
        return unitPrice;
    }
    public void setUnitPrice(int unitPrice){
        this.unitPrice = unitPrice;
    }
    
    public int getStock(){
        return stock;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public String getProductName(){
        return productName;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getSize(){
        return size;
    }
    public void setSize(String size){
        this.size = size;
    }
    public int getLineTotal(){
        return amount * unitPrice;
    }
}