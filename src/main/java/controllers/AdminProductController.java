// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Product;
import service.ProductService;

@WebServlet(
   name = "AdminProductController",
   urlPatterns = {"/admin/products"}
)
public class AdminProductController extends HttpServlet {
   private final ProductService productService = new ProductService();

   public AdminProductController() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      List<Product> listProducts = this.productService.getAllProduct();
      request.setAttribute("listProducts", listProducts);
      request.setAttribute("pageTitle", "Quản lý Sản phẩm");
      request.getRequestDispatcher("/WEB-INF/AdminViews/listProduct.jsp").forward(request, response);
   }
}
