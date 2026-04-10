package model;

import java.util.Date;

public class Coupon {
    private String id;
    private String type;
    private int value;
    private int maxDiscount;
    private int minCost;
    private Date startedDate;
    private Date expiredDate;
    private boolean active;

    public Coupon() {
    }

    public Coupon(String id, String type, int value, int maxDiscount, int minCost,
                  Date startedDate, Date expiredDate, boolean active) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.maxDiscount = maxDiscount;
        this.minCost = minCost;
        this.startedDate = startedDate;
        this.expiredDate = expiredDate;
        this.active = active;
    }

    // Getter & Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean IsActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}