package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;
import model.User;
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
        
        //Lấy id sản phẩm cần xem thông tin chi tiết
        String productId = request.getParameter("id");
        
        //Trở về home khi lỗi ko truyền id
        if (productId == null || productId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
            return;
        }

        //Lấy phân loại cho sản phẩm bằng id
        Product product = productService.getProductDetail(productId);
        
        //Quay về home nếu ko có sphaan loại nào
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
            return;
        }

        //Xử lý log view
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser != null) {
            productService.logInteraction(currentUser.getId(), productId, "VIEW");
        }

        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/CustomerViews/productdetail.jsp").forward(request, response);
    }
}