package controllers;

import dao.ProductDAO;
import model.CartItem;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/seller/checkout")
public class CheckoutServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        List<Product> products = productDAO.getAllProducts();

        if (keyword != null && !keyword.trim().isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) cart = new ArrayList<>();

        int subtotal = cart.stream().mapToInt(CartItem::getLineTotal).sum();
        int shipping = subtotal >= 1000000 ? 0 : 30000;
        int total = subtotal + shipping;

        request.setAttribute("products", products);
        request.setAttribute("cart", cart);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total", total);

        request.getRequestDispatcher("/WEB-INF/seller/checkout.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) cart = new ArrayList<>();

        String action = request.getParameter("action");
        String variantId = request.getParameter("productVariantId");

        try {

            if ("add".equals(action)) {

                boolean found = false;

                for (CartItem item : cart) {
                    if (item.getProductVariantId().equals(variantId)) {
                        item.setAmount(item.getAmount() + 1);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    CartItem item = new CartItem();
                    item.setProductVariantId(variantId);
                    item.setProductName("Sản phẩm"); // demo
                    item.setAmount(1);
                    item.setUnitPrice(100000); // demo

                    cart.add(item);
                }

            } else if ("increase".equals(action)) {

                for (CartItem item : cart) {
                    if (item.getProductVariantId().equals(variantId)) {
                        item.setAmount(item.getAmount() + 1);
                    }
                }

            } else if ("decrease".equals(action)) {

                cart.removeIf(item -> {
                    if (item.getProductVariantId().equals(variantId)) {
                        item.setAmount(item.getAmount() - 1);
                        return item.getAmount() <= 0;
                    }
                    return false;
                });

            } else if ("remove".equals(action)) {

                cart.removeIf(item -> item.getProductVariantId().equals(variantId));

            } else if ("checkout".equals(action)) {

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