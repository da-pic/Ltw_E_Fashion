// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package service;

import dao.OrderDAO;

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
}
