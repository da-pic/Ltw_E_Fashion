// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package service;

import dao.OrderDAO;
import java.util.List;
import java.util.Map;

public class OrderService {
   private final OrderDAO orderDAO = new OrderDAO();

   public OrderService() {
   }

   public int getTotalOrders() {
      return this.orderDAO.getTotalOrders();
   }

   public long getTotalRevenue() {
      return this.orderDAO.getTotalRevenue();
   }
   
    public List<Map<String, Object>> getOrderHistory(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        return orderDAO.getOrderHistory(userId);
    }

    public boolean cancelOrder(String orderId, String userId) {
        if (orderId == null || orderId.trim().isEmpty() || userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return orderDAO.cancelOrder(orderId, userId);
    }
}
