package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chinh
 */
public class Order {
    private String id;
    private String user_id;
    private int total_price;
    private int shipping_fee;
    private String payment_method;
    private String coupon_id;
    private int address_id;
    private String order_status;
    private String payment_status;
    private Timestamp created_at; 
    
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public Order(String id, String user_id, int total_price, int shipping_fee, String payment_method, 
                 String coupon_id, int address_id, String order_status, String payment_status, Timestamp created_at) {
        this.id = id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.shipping_fee = shipping_fee;
        this.payment_method = payment_method;
        this.coupon_id = coupon_id;
        this.address_id = address_id;
        this.order_status = order_status;
        this.payment_status = payment_status;
        this.created_at = created_at;
        this.orderItems = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(int shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }
}