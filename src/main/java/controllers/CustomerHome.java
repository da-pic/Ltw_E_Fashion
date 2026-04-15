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
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.User;

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
        //Kiểm tra session
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String userId = (currentUser != null) ? currentUser.getId() : null;
        
        //Lấy danh sách danh mục
        List<Category> categories = productService.getAllCategories();
        request.setAttribute("categories", categories);
        
        //Kiểm tra trạng thái đang chọn
        String categoryIdStr = request.getParameter("categoryId");
        List<Product> listProduct;
       
        //Lấy danh sách sản phẩm theo id danh mục nếu ko sẽ lấy danh sách đc đề xuất
        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            int categoryId = Integer.parseInt(categoryIdStr);
            listProduct = productService.getProductsByCategory(categoryId);
            request.setAttribute("selectedCategoryId", categoryId);
            request.setAttribute("sectionTitle", "SẢN PHẨM THEO DANH MỤC");
        } else {
            listProduct = productService.getProductsForHomeDisplay(userId);
            request.setAttribute("sectionTitle", "GỢI Ý HÔM NAY");
            List<Product> topSearched = productService.getTopSearchedProducts();
            request.setAttribute("topSearched", topSearched);
            List<Product> flashSaleProducts = productService.getFlashSaleProducts();
            request.setAttribute("flashSaleProducts", flashSaleProducts);
        }
        
        request.setAttribute("danhSachSP", listProduct);
        request.getRequestDispatcher("/WEB-INF/CustomerViews/productList.jsp").forward(request, response);
    }
}
