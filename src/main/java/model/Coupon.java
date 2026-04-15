package model;

import java.sql.Date;

public class Coupon {
    private String id;
    private String type; 
    private int value;
    private int max_discount;
    private int min_cost;
    private Date started_date;
    private Date expired_date;
    private boolean is_active;

    public Coupon() {
    }

    public Coupon(String id, String type, int value, int max_discount, int min_cost, Date started_date, Date expired_date, boolean is_active) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.max_discount = max_discount;
        this.min_cost = min_cost;
        this.started_date = started_date;
        this.expired_date = expired_date;
        this.is_active = is_active;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public int getMax_discount() { return max_discount; }
    public void setMax_discount(int max_discount) { this.max_discount = max_discount; }

    public int getMin_cost() { return min_cost; }
    public void setMin_cost(int min_cost) { this.min_cost = min_cost; }

    public Date getStarted_date() { return started_date; }
    public void setStarted_date(Date started_date) { this.started_date = started_date; }

    public Date getExpired_date() { return expired_date; }
    public void setExpired_date(Date expired_date) { this.expired_date = expired_date; }

    public boolean isIs_active() { return is_active; }
    public void setIs_active(boolean is_active) { this.is_active = is_active; }
}