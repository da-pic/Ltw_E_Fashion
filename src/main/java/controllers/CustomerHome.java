package controllers;

import java.util.*;
import model.Product;
import dao.ProductDAO;
import service.ProductService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Chinh
 */
@WebServlet(name = "CustomerHome", urlPatterns = {"/CustomerHome"})
public class CustomerHome extends HttpServlet {
    private ProductService productService = new ProductService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Product> listProduct = productService.getAllProduct();
        request.setAttribute("danhSachSP", listProduct);
        
        request.getRequestDispatcher("/WEB-INF/CustomerViews/productList.jsp").forward(request, response);

}
}
