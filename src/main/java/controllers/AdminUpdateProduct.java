package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Product;
import service.ProductService;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)

@WebServlet(urlPatterns = {"/admin/products/update"})
public class AdminUpdateProduct extends HttpServlet { 

    private ProductService productService = new ProductService();
    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        Product product = productService.getProductDetail(id); 
        
        if (product != null) {
            request.setAttribute("product", product);
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.setAttribute("suppliers", supplierDAO.getAllSuppliers()); // thêm supplier
            request.getRequestDispatcher("/WEB-INF/AdminViews/productUpdate.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        try {
            String id = request.getParameter("productId"); 
            String name = request.getParameter("productName");
            String supplierId = request.getParameter("supplierId");
            if (supplierId != null && supplierId.trim().isEmpty()) {
                supplierId = null; 
            }
            String brandId = request.getParameter("brandId");
            if (brandId != null && brandId.trim().isEmpty()) {
                brandId = null;
            }
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String description = request.getParameter("description");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            Product updatedProduct = new Product(
                    id, name, brandId, supplierId,
                    categoryId, isActive, description, 0, null
            );

            String[] varIds = request.getParameterValues("varId");
            String[] varColors = request.getParameterValues("varColor");
            String[] varSizes = request.getParameterValues("varSize");
            String[] varPrices = request.getParameterValues("varPrice");
            String[] varStocks = request.getParameterValues("varStock");

            String[] varExistingImages = request.getParameterValues("varExistingImage");

            List<Part> fileParts = new ArrayList<>();
            for (Part part : request.getParts()) {
                if ("varImageFile".equals(part.getName())) {
                    fileParts.add(part);
                }
            }

            String[] finalImages = new String[varIds.length];
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            for (int i = 0; i < varIds.length; i++) {
                Part filePart = fileParts.get(i);
                String existingImage = varExistingImages[i];

                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName; // Tránh trùng tên
                    filePart.write(uploadPath + File.separator + uniqueFileName);
                    finalImages[i] = "/images/" + uniqueFileName;
                } else {
                    finalImages[i] = (existingImage != null && !existingImage.isEmpty()) ? existingImage : null;
                }
            }

            boolean isSuccess = productService.updateProductWithVariants(
                    updatedProduct, varIds, varColors, varSizes, varPrices, varStocks, finalImages
            );

            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/admin/products");
            } else {
                throw new Exception("Update thất bại");
            }

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("product", productService.getProductDetail(request.getParameter("productId")));
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.setAttribute("suppliers", supplierDAO.getAllSuppliers());

            request.getRequestDispatcher("/WEB-INF/AdminViews/productUpdate.jsp").forward(request, response);
        }
    }
}