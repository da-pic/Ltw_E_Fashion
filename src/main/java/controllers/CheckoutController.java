package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.CartItem;
import model.User;
import model.Address;
import service.CheckoutService;
import service.ProductService;
import service.CouponService;

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    private CouponService couponService = new CouponService();
    private CheckoutService checkoutService = new CheckoutService();
    private ProductService productService = new ProductService();

    // ===================== GET =====================
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

        try {
            // ====== MUA NGAY ======
            if ("buy".equals(action)) {

                String variantId = request.getParameter("variantId");
                int quantity = 1;

                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                } catch (Exception e) {}

                checkoutItems = checkoutService.getItemsForCheckoutDirectly(variantId, quantity);

            } 
            // ====== TỪ GIỎ HÀNG ======
            else {
                String[] selectedVariantIds = request.getParameterValues("selectedVariants");

                if (selectedVariantIds != null && selectedVariantIds.length > 0) {
                    checkoutItems = checkoutService.getItemsForCheckoutFromCart(userId, selectedVariantIds);
                }
            }

            // ====== VALIDATE ======
            if (checkoutItems == null || checkoutItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/CustomerHome");
                return;
            }

            for (CartItem item : checkoutItems) {
                item.setProductVariant(
                        productService.getProductVariantById(item.getProductVariantId())
                );
            }

            // ====== DATA ======
            List<Address> addresses = checkoutService.getUserAddresses(userId);
            int grandTotal = checkoutService.calculateCheckoutTotal(checkoutItems);

            List<Map<String, Object>> myVouchers = couponService.getMyVouchers(userId);

            // ====== SET ATTRIBUTE ======
            request.setAttribute("cartId", request.getParameter("cartId"));
            request.setAttribute("checkoutItems", checkoutItems);
            request.setAttribute("addresses", addresses);
            request.setAttribute("grandTotal", grandTotal);
            request.setAttribute("myVouchers", myVouchers);

            request.getRequestDispatcher("/WEB-INF/CustomerViews/checkout.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/CustomerHome");
        }
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
            String userId = currentUser.getId();

            String cartId = request.getParameter("cartId");
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String paymentMethod = request.getParameter("paymentMethod");

            String couponId = request.getParameter("appliedCoupon");
            if (couponId != null && couponId.trim().isEmpty()) {
                couponId = null;
            }

            String[] variantIds = request.getParameterValues("variantIds");
            String[] quantities = request.getParameterValues("quantities");

            // ====== VALIDATE ======
            if (variantIds == null || quantities == null) {
                response.sendRedirect(request.getContextPath() + "/checkout?error=missing_items");
                return;
            }

            // ====== PROCESS ORDER ======
            boolean success = checkoutService.processOrder(
                    userId,
                    cartId,
                    addressId,
                    paymentMethod,
                    couponId,
                    variantIds,
                    quantities
            );

            if (success) {
                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                response.sendRedirect(request.getContextPath() + "/checkout?error=failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/checkout?error=exception");
        }
    }
}