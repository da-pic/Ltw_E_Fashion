// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OrderService;
import service.UserService;

@WebServlet(
   name = "AdminController",
   urlPatterns = {"/admin/dashboard"}
)
public class AdminDashboard extends HttpServlet {
   private UserService userService = new UserService();
   private OrderService orderService = new OrderService();

   public AdminDashboard() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int totalUsers = this.userService.getAllUsers().size();
      int totalOrders = this.orderService.getTotalOrders();
      long totalRevenue = this.orderService.getTotalRevenue();
      
      request.setAttribute("totalUsers", totalUsers);
      request.setAttribute("totalOrders", totalOrders);
      request.setAttribute("totalRevenue", totalRevenue);
      request.setAttribute("pageTitle", "Admin Dashboard");
      request.getRequestDispatcher("/WEB-INF/AdminViews/dashboard.jsp").forward(request, response);
   }
}
