package controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.User;
import model.Address;
import service.CheckoutService;
import service.ProductService;
import service.CouponService; // Thay thế DAO bằng Service

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    
    private CouponService couponService = new CouponService();
    private CheckoutService checkoutService = new CheckoutService();
    private ProductService productService = new ProductService();

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
        List<CartItem> checkoutItems = null;

        String action = request.getParameter("action"); 
        
        if ("buy".equals(action)) {
            String variantId = request.getParameter("variantId");
            int quantity = 1;
            try {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            } catch (NumberFormatException e) {}
            
            checkoutItems = checkoutService.getItemsForCheckoutDirectly(variantId, quantity);
            
        } else {
            String[] selectedVariantIds = request.getParameterValues("selectedVariants");
            if (selectedVariantIds != null && selectedVariantIds.length > 0) {
                checkoutItems = checkoutService.getItemsForCheckoutFromCart(userId, selectedVariantIds);
            }
        }

        if (checkoutItems == null || checkoutItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
            return;
        }

        List<Address> addresses = checkoutService.getUserAddresses(userId);
        int grandTotal = checkoutService.calculateCheckoutTotal(checkoutItems);
        
        List<java.util.Map<String, Object>> myVouchers = couponService.getMyVouchers(userId);
        request.setAttribute("myVouchers", myVouchers);
        
        request.setAttribute("cartId", request.getParameter("cartId"));
        request.setAttribute("checkoutItems", checkoutItems);
        request.setAttribute("addresses", addresses);
        request.setAttribute("grandTotal", grandTotal);
        
        request.getRequestDispatcher("/WEB-INF/CustomerViews/checkout.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            String cartId = request.getParameter("cartId");
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String paymentMethod = request.getParameter("paymentMethod");
            
            String couponId = request.getParameter("appliedCoupon");

            if (couponId != null && couponId.trim().isEmpty()) {
                couponId = null; 
            }
            
            String[] variantIds = request.getParameterValues("variantIds");
            String[] quantities = request.getParameterValues("quantities");

            boolean success = checkoutService.processOrder(currentUser.getId(), cartId, addressId, paymentMethod, couponId, variantIds, quantities);

            if (success) {  
                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                response.sendRedirect(request.getContextPath() + "/checkout?error=failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
        }
    }
}