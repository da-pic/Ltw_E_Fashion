package controllers;

import java.io.IOException;

import dao.BrandDAO;
import dao.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import service.ProductService;

@WebServlet(urlPatterns = {"/admin/products/update"})
public class AdminUpdateProduct extends HttpServlet { 

    private ProductService productService = new ProductService();
    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Product product = productService.getProductDetail(id); 
        
        if (product != null) {
            request.setAttribute("product", product);
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/AdminViews/productUpdate.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String id = request.getParameter("productId"); 
        String name = request.getParameter("productName");
        String brandId = request.getParameter("brandId");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String description = request.getParameter("description");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        
        Product updatedProduct = new Product(id, name, brandId, categoryId, isActive, description, null, null);
        
        String[] varIds = request.getParameterValues("varId");
        String[] varColors = request.getParameterValues("varColor");
        String[] varSizes = request.getParameterValues("varSize");
        String[] varPrices = request.getParameterValues("varPrice");
        String[] varStocks = request.getParameterValues("varStock");
        
        boolean isSuccess = productService.updateProductWithVariants(updatedProduct, varIds, varColors, varSizes, varPrices, varStocks);
        
        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } else {
            request.setAttribute("error", "Có lỗi xảy ra trong quá trình cập nhật cơ sở dữ liệu!");
            request.setAttribute("product", productService.getProductDetail(id));
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/AdminViews/productUpdate.jsp").forward(request, response);
        }
    }
}