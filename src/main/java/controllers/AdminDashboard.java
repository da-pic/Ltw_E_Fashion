// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import service.OrderService;
import service.UserService;
import service.ProductService;

@WebServlet(
   name = "AdminDashboard",
   urlPatterns = {"/admin/dashboard"}
)
public class AdminDashboard extends HttpServlet {
   private UserService userService = new UserService();
   private OrderService orderService = new OrderService();
   private ProductService productService = new ProductService();
   public AdminDashboard() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int totalUsers = this.userService.getAllUsers().size();
      int totalOrders = this.orderService.getTotalOrders();
      long totalRevenue = this.orderService.getTotalRevenue();
      int totalProducts = this.productService.getAllProduct().size();
      NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
      String formattedRevenue = format.format(totalRevenue);
      request.setAttribute("totalProducts", totalProducts);
      request.setAttribute("totalUsers", totalUsers);
      request.setAttribute("totalOrders", totalOrders);
      request.setAttribute("totalRevenue", formattedRevenue);
      request.setAttribute("pageTitle", "Admin Dashboard");
      request.getRequestDispatcher("/WEB-INF/AdminViews/dashboard.jsp").forward(request, response);
   }
}
