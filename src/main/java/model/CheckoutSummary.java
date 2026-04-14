package model;

public class CheckoutSummary {

    private int subtotal;
    private int shippingFee;
    private int discount;
    private int finalTotal;

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }

    public int getShippingFee() { return shippingFee; }
    public void setShippingFee(int shippingFee) { this.shippingFee = shippingFee; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    public int getFinalTotal() { return finalTotal; }
    public void setFinalTotal(int finalTotal) { this.finalTotal = finalTotal; }
}