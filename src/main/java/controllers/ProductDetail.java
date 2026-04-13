/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import service.ProductService;

/**
 *
 * @author Chinh
 */
@WebServlet(name = "ProductDetail", urlPatterns = {"/productdetail"})
public class ProductDetail extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productId = request.getParameter("id");
        
        Product product = productService.getProductDetail(productId);
        
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/CustomerViews/productdetail.jsp").forward(request, response);
    }
}