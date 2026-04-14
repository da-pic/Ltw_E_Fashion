package controllers;
 
import model.Product;
import service.ProductService;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
 
@WebServlet("/seller/product")
public class ProductController extends HttpServlet {
 
    private final ProductService productService = new ProductService();
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
 
        String action = req.getParameter("action");
        if (action == null) action = "list";
        
        if("add".equals(action)){
            req.getRequestDispatcher("/WEB-INF/seller/addProduct.jsp").forward(req, resp);
        }
 
        else if ("edit".equals(action)) {
            String id = req.getParameter("id");
            Product product = productService.getProductById(id);
            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/seller/product");
                return;
            }
            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/seller/editProduct.jsp").forward(req, resp);
 
        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            productService.deleteProduct(id);
            resp.sendRedirect(req.getContextPath() + "/seller/product?msg=deleted");
 
        } else {
            List<Product> products = productService.getAllProducts();
            req.setAttribute("products", products);
            req.getRequestDispatcher("/WEB-INF/seller/productList.jsp").forward(req, resp);
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
 
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            Product product = new Product();
            product.setId(req.getParameter("id"));
            product.setProductName(req.getParameter("productName"));
            product.setBrandId(req.getParameter("brandId"));
            product.setCategoryId(req.getParameter("categoryId"));
            product.setIsActive(1);
            product.setDescription(req.getParameter("description"));
 
            boolean success = productService.addProduct(product);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/seller/product?msg=added");
            } else {
                req.setAttribute("error", "Thêm sản phẩm thất bại. ID có thể đã tồn tại!");
                req.setAttribute("product", product);
                req.getRequestDispatcher("/WEB-INF/seller/addProduct.jsp").forward(req, resp);
            }
 
        } else  if ("update".equals(action)) {
            Product product = new Product();
            product.setId(req.getParameter("id"));
            product.setProductName(req.getParameter("productName"));
            product.setBrandId(req.getParameter("brandId"));
            product.setCategoryId(req.getParameter("categoryId"));
            product.setIsActive(Integer.parseInt(req.getParameter("isActive")));
            product.setDescription(req.getParameter("description"));
 
            boolean success = productService.updateProduct(product);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/seller/product?msg=updated");
            } else {
                req.setAttribute("error", "Cập nhật thất bại. Vui lòng thử lại.");
                req.setAttribute("product", product);
                req.getRequestDispatcher("/WEB-INF/seller/editProduct.jsp").forward(req, resp);
            }
        }
    }
}
 