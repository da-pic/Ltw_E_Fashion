package controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import service.ProductService;

// Đường dẫn được bảo vệ bởi AdminFilter
@WebServlet(urlPatterns = {"/admin/products"})  
public class AdminProduct extends HttpServlet {

    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        
        if ("/admin/products".equals(path)) {
            // Lấy danh sách toàn bộ sản phẩm
            List<Product> productList = productService.getAllProduct();
            
            // Đưa danh sách vào request attribute
            request.setAttribute("productList", productList);
            
            // Chuyển hướng tới trang product-manager.jsp
            request.getRequestDispatcher("/WEB-INF/AdminViews/productManager.jsp").forward(request, response);
        }
    }
}