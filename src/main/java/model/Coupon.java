
package model;

import java.sql.Date;
public class Coupon {
    private String id;
    private String type;
    private int value;
    private int maxDiscount;
    private int minCost;
    private Date startedDate;
    private Date expiredDate;
    private boolean active;
    private int userLimit;
    
    public Coupon(){
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public int getMaxDiscount(){
        return maxDiscount;
    }
    public void setMaxDiscount(int maxDiscount){
        this.maxDiscount = maxDiscount;
    }
    public int getMinCost(){
        return minCost;
    }
    public void setMinCost(int minCost){
        this.minCost = minCost;
    }
    public Date getStartedDate(){
        return startedDate;
    }
    public void setStartedDate(Date startedDate){
        this.startedDate = startedDate;
    }
    public Date getExpiredDate(){
        return expiredDate;
    }
    public void setExpiredDate(Date expiredDate){
        this.expiredDate = expiredDate;
    }
    private boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public int getUserLimit() {
        return userLimit;
    }
    public void setUserLimit(int userLimit){
        this.userLimit = userLimit;
    }
}
