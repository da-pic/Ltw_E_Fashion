package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;
import service.CartService;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userId = currentUser.getId();
        String action = request.getParameter("action");
        String variantId = request.getParameter("variantId");

        try {
            if ("add".equals(action)) {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                cartService.addToCart(userId, variantId, quantity);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
                
            } else if ("remove".equals(action)) {
                cartService.removeItem(userId, variantId);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            } else if ("decrease".equals(action)) {
                cartService.decreaseItem(userId, variantId);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            
            Cart cart = cartService.getCartForUser(userId);
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/WEB-INF/CustomerViews/cart.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}