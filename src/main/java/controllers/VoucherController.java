package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.CouponService;

@WebServlet(name = "VoucherController", urlPatterns = {"/my-vouchers"})
public class VoucherController extends HttpServlet {

    private CouponService couponService = new CouponService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        // Yêu cầu đăng nhập mới được xem ví
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Gọi Service lấy danh sách Ví Voucher của user này
        List<Map<String, Object>> myVouchers = couponService.getMyVouchers(currentUser.getId());
        
        request.setAttribute("vouchers", myVouchers);
        request.getRequestDispatcher("/WEB-INF/CustomerViews/vouchers.jsp").forward(request, response);
    }
}