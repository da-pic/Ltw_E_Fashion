package controllers;

import dao.BrandDAO;
import dao.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet(urlPatterns = {"/admin/products/add"})
public class AdminAddProduct extends HttpServlet {

    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("brands", brandDAO.getAllBrands());
        request.setAttribute("categories", categoryDAO.getAllCategories());
        
        request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String id = request.getParameter("productId");
        String name = request.getParameter("productName");
        String brandId = request.getParameter("brandId");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String description = request.getParameter("description");
        
        // Cập nhật Constructor khớp với Product.java: 
        // id, product_name, brand_id, category_id, is_active, description, display_price, display_image
        Product newProduct = new Product(id, name, brandId, categoryId, true, description, null, null);
        
        service.ProductService productService = new service.ProductService();
        boolean isSuccess = productService.addProduct(newProduct);
        
        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } else {
            request.setAttribute("error", "Lỗi: Mã sản phẩm đã tồn tại hoặc dữ liệu không hợp lệ!");
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp").forward(request, response);
        }
    }
}