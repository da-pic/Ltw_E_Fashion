package controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; 
import model.Product;
import model.User; 
import model.Category; 
import service.ProductService;

@WebServlet(name = "SearchController", urlPatterns = {"/search"})
public class SearchController extends HttpServlet {

    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String keyword = request.getParameter("keyword");
        
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
            return;
        }

        List<Product> searchResults = productService.searchProducts(keyword);
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser != null && searchResults != null && !searchResults.isEmpty()) {
            productService.logSearchInteractions(currentUser.getId(), searchResults);
        }
        
        List<Category> categories = productService.getAllCategories();
        request.setAttribute("categories", categories);

        request.setAttribute("danhSachSP", searchResults);
        request.setAttribute("sectionTitle", "KẾT QUẢ TÌM KIẾM CHO: '" + keyword.toUpperCase() + "'");
        
        request.getRequestDispatcher("/WEB-INF/CustomerViews/productList.jsp").forward(request, response);
    }
}