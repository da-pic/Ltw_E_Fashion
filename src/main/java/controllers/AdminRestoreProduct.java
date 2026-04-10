package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

@WebServlet(urlPatterns = {"/admin/products/restore"})
public class AdminRestoreProduct extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        
        if (id != null && !id.trim().isEmpty()) {
            productService.restoreProduct(id);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}