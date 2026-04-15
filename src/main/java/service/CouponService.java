package service;

import dao.CouponDAO;
import java.util.List;
import java.util.Map;
import model.Coupon;

public class CouponService {
    
    private CouponDAO couponDAO = new CouponDAO();

    public List<Map<String, Object>> getMyVouchers(String userId) {
        if (userId == null || userId.isEmpty()) {
            return null;
        }
        return couponDAO.getMyVouchers(userId);
    }
    
    public int calculateDiscountAmount(String userId, String couponId, int orderTotal) {
        if (couponId == null || couponId.isEmpty() || orderTotal <= 0) {
            return 0;
        }
        
        Coupon coupon = couponDAO.getValidCoupon(userId, couponId, orderTotal);
        if (coupon == null) return 0;
        if (coupon == null) {
            return 0; 
        }
        
        int discountAmount = 0;
        if ("percent".equalsIgnoreCase(coupon.getType())) {
            discountAmount = (orderTotal * coupon.getValue()) / 100;
            if (coupon.getMax_discount() > 0 && discountAmount > coupon.getMax_discount()) {
                discountAmount = coupon.getMax_discount();
            }
        } else {
            discountAmount = coupon.getValue();
        }
        
        return discountAmount;
    }
}