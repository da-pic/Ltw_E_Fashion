package controllers;

import model.CartItem;
import model.Product;
import service.CartService;
import service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/seller/checkout")
public class CheckoutServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final CartService    cartService    = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        List<Product> products = productService.getAllProducts();

        if (keyword != null && !keyword.trim().isEmpty()) {
            final String kw = keyword.toLowerCase();
            products = products.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(kw))
                    .toList();
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = cartService.initCart(
                (List<CartItem>) session.getAttribute("cart"));

        int subtotal = cartService.calculateSubtotal(cart);
        int shipping = cartService.calculateShipping(subtotal);
        int total    = cartService.calculateTotal(subtotal, shipping);

        request.setAttribute("products", products);
        request.setAttribute("cart",     cart);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total",    total);

        request.getRequestDispatcher("/WEB-INF/seller/checkout.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<CartItem> cart = cartService.initCart(
                (List<CartItem>) session.getAttribute("cart"));

        String action    = request.getParameter("action");
        String variantId = request.getParameter("productVariantId");

        try {
            switch (action == null ? "" : action) {
                case "add":
                    cartService.addItem(cart, variantId);
                    break;
                case "increase":
                    cartService.increaseItem(cart, variantId);
                    break;
                case "decrease":
                    cartService.decreaseItem(cart, variantId);
                    break;
                case "remove":
                    cartService.removeItem(cart, variantId);
                    break;
                case "checkout":
                    session.removeAttribute("cart");
                    response.sendRedirect("order-success");
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("checkout");
    }
}