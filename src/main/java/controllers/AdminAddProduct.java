package controllers;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Product;
import service.ProductService;

import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/products/add"})
public class AdminAddProduct extends HttpServlet {

    private ProductService productService = new ProductService();
    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("brands", brandDAO.getAllBrands());
        request.setAttribute("categories", categoryDAO.getAllCategories());
        request.setAttribute("suppliers", supplierDAO.getAllSuppliers());
        

        request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // ===== BASIC INFO =====
            String id = request.getParameter("productId");
            String name = request.getParameter("productName");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // ===== BRAND =====
            String brandSelect = request.getParameter("brandSelect");
            String brandInput = request.getParameter("brandInput");

            String brandId;

            if (brandInput != null && !brandInput.trim().isEmpty()) {
                brandId = brandDAO.addNewBrand(brandInput.trim());
            } else {
                brandId = brandSelect;
            }

            String supplierSelect = request.getParameter("supplierSelect");
            String supplierInput = request.getParameter("supplierInput");

            String supplierId;

            if (supplierInput != null && !supplierInput.trim().isEmpty()) {
                supplierId = supplierDAO.addNewSupplier(supplierInput.trim());
            } else {
                supplierId = supplierSelect;
            }

            if (brandId == null || brandId.isEmpty()) {
                throw new Exception("Vui lòng chọn hoặc nhập thương hiệu");
            }

            if (supplierId == null || supplierId.isEmpty()) {
                throw new Exception("Vui lòng chọn hoặc nhập nhà cung cấp");
            }

            Product newProduct = new Product(
                    id,
                    name,
                    brandId,
                    supplierId,
                    categoryId,
                    true,
                    description,
                    0,
                    null
            );

            boolean isSuccess = productService.addProduct(newProduct);

            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/admin/products");
            } else {
                throw new Exception("Không thể thêm sản phẩm!");
            }

        } catch (Exception e) {
            
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.setAttribute("suppliers", supplierDAO.getAllSuppliers());

            request.setAttribute("error", e.getMessage());

            request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp")
                   .forward(request, response);
        }
    }
}