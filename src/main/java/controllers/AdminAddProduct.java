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
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)

@WebServlet(urlPatterns = {"/admin/products/add"})
public class AdminAddProduct extends HttpServlet {

    private ProductService productService = new ProductService();
    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        loadCategory(request);
        request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ===== GIỮ DATA =====
        request.setAttribute("productId", request.getParameter("productId"));
        request.setAttribute("productName", request.getParameter("productName"));
        request.setAttribute("brandInput", request.getParameter("brandInput"));
        request.setAttribute("supplierInput", request.getParameter("supplierInput"));
        request.setAttribute("description", request.getParameter("description"));

        // ===== LOAD CATEGORY =====
        loadCategory(request);

        String action = request.getParameter("action");

        if (!"save".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp")
                   .forward(request, response);
            return;
        }

        try {
            // ===== BASIC =====
            String id = request.getParameter("productId");
            String name = request.getParameter("productName");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // ===== BRAND =====
            String brandInput = request.getParameter("brandInput");
            if (brandInput == null || brandInput.trim().isEmpty()) {
                throw new Exception("Vui lòng nhập thương hiệu");
            }
            String brandId = brandDAO.addNewBrand(brandInput.trim());

            // ===== SUPPLIER =====
            String supplierInput = request.getParameter("supplierInput");
            if (supplierInput == null || supplierInput.trim().isEmpty()) {
                throw new Exception("Vui lòng nhập nhà cung cấp");
            }
            String supplierId = supplierDAO.addNewSupplier(supplierInput.trim());

            // ===== LẤY THÔNG TIN PHÂN LOẠI & ẢNH =====
            String[] varColors = request.getParameterValues("varColor");
            String[] varSizes = request.getParameterValues("varSize");
            String[] varPrices = request.getParameterValues("varPrice");
            String[] varStocks = request.getParameterValues("varStock");

            if (varColors == null || varColors.length == 0) {
                throw new Exception("Vui lòng thêm ít nhất 1 phân loại sản phẩm!");
            }

            // Xử lý upload file
            List<Part> fileParts = new ArrayList<>();
            for (Part part : request.getParts()) {
                if ("varImageFile".equals(part.getName())) {
                    fileParts.add(part);
                }
            }

            String[] finalImages = new String[varColors.length];
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            for (int i = 0; i < varColors.length; i++) {
                Part filePart = (i < fileParts.size()) ? fileParts.get(i) : null;
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                    filePart.write(uploadPath + File.separator + uniqueFileName);
                    finalImages[i] = "/images/" + uniqueFileName;
                } else {
                    finalImages[i] = null;
                }
            }

            // ===== SAVE =====
            Product newProduct = new Product(id, name, brandId, supplierId,
                    categoryId, true, description, 0, null);

            if (productService.addProductWithVariants(newProduct, varColors, varSizes, varPrices, varStocks, finalImages)) {
                response.sendRedirect(request.getContextPath() + "/admin/products");
            } else {
                throw new Exception("Lỗi: Mã sản phẩm bị trùng hoặc thiếu thông tin!");
            }

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/AdminViews/productAdd.jsp")
                   .forward(request, response);
        }
    }

    private void loadCategory(HttpServletRequest request) {

        String parentId = request.getParameter("parentId");
        String childId = request.getParameter("childId");

        request.setAttribute("parentCategories", categoryDAO.getParentCategories());

        if (parentId != null && !parentId.isEmpty()) {
            request.setAttribute("childCategories",
                    categoryDAO.getChildCategories(Integer.parseInt(parentId)));
            request.setAttribute("selectedParent", Integer.parseInt(parentId));
        }

        if (childId != null && !childId.isEmpty()) {
            request.setAttribute("grandChildCategories",
                    categoryDAO.getChildCategories(Integer.parseInt(childId)));
            request.setAttribute("selectedChild", Integer.parseInt(childId));
        }
        
        String categoryId = request.getParameter("categoryId");

        if (categoryId != null && !categoryId.isEmpty()) {
            request.setAttribute("selectedCategory", Integer.parseInt(categoryId));
        }
    }
}