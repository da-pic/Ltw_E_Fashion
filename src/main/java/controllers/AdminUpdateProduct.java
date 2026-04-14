package controllers;

import java.io.IOException;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductVariantDAO;
import dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import model.Product;
import service.ProductService;

@WebServlet(urlPatterns = {"/admin/products/update"})
@MultipartConfig
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
            request.setAttribute("suppliers", supplierDAO.getAllSuppliers());
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
            // ===== PRODUCT =====
            String id = request.getParameter("productId"); 
            String name = request.getParameter("productName");
            String brandId = request.getParameter("brandId");
            String supplierId = request.getParameter("supplierId");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            String description = request.getParameter("description");
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            Product updatedProduct = new Product(
                    id, name, brandId, supplierId,
                    categoryId, isActive, description, 0, null
            );
            String deletedIdsRaw = request.getParameter("deletedVariantIds");

            if (deletedIdsRaw != null && !deletedIdsRaw.isEmpty()) {
                String[] deletedIds = deletedIdsRaw.split(",");

                for (String vid : deletedIds) {
                    new ProductVariantDAO().deleteProductVariant(vid);
                }
            }
            // ===== VARIANT ARRAY =====
            String[] varIds = request.getParameterValues("varId");
            String[] varColors = request.getParameterValues("varColor");
            String[] varSizes = request.getParameterValues("varSize");
            String[] varPrices = request.getParameterValues("varPrice");
            String[] varImportPrices = request.getParameterValues("varImportPrice");
            String[] varStocks = request.getParameterValues("varStock");
            String[] varActives = request.getParameterValues("varActive");

            Collection<Part> parts = request.getParts();
            List<Part> imageParts = parts.stream()
                    .filter(p -> "varImage".equals(p.getName()))
                    .toList();

            List<String> imageNames = new ArrayList<>();

            String uploadPath = getServletContext().getRealPath("/images");
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            for (int i = 0; i < varColors.length; i++) {

                Part part = (i < imageParts.size()) ? imageParts.get(i) : null;

                if (part != null && part.getSize() > 0) {

                    String fileName = UUID.randomUUID() + "_" + part.getSubmittedFileName();
                    part.write(uploadPath + File.separator + fileName);

                    imageNames.add(fileName);
                } else {
                    imageNames.add(null);
                }
            }

            while (imageNames.size() < varColors.length) {
                imageNames.add(null);
            }

            boolean isSuccess = productService.updateProductWithVariantsFull(
                    updatedProduct,
                    varIds,
                    varColors,
                    varSizes,
                    varPrices,
                    varImportPrices,
                    varStocks,
                    varActives,
                    imageNames
            );

            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/admin/products");
            } else {
                throw new Exception("Update thất bại");
            }

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error", e.getMessage());
            request.setAttribute("product", productService.getProductDetail(request.getParameter("productId")));
            request.setAttribute("brands", brandDAO.getAllBrands());
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.setAttribute("suppliers", supplierDAO.getAllSuppliers());

            request.getRequestDispatcher("/WEB-INF/AdminViews/productUpdate.jsp").forward(request, response);
        }
    }
}