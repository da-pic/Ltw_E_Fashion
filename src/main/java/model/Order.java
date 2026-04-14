package model;

public class Order {
    private String id;
    private String userId;
    private int totalPrice;
    private String status;
    private String paymentMethod;
    private int shippingFee;
    private int addressId;
    private String couponId;
    public Order() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public int getShippingFee() {
    return shippingFee;
}

public void setShippingFee(int shippingFee) {
    this.shippingFee = shippingFee;
}
public int getAddressId() {
    return addressId;
}

public void setAddressId(int addressId) {
    this.addressId = addressId;
}
public String getCouponId() {
    return couponId;
}

public void setCouponId(String couponId) {
    this.couponId = couponId;
}
}