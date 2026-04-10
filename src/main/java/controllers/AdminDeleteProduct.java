package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ProductService;

@WebServlet(urlPatterns = {"/admin/products/delete"})
public class AdminDeleteProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            new ProductService().softDeleteProduct(id);
        }
        // Xóa xong thì quay về trang danh sách
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}