package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.Coupon;
import service.CouponService;

@WebServlet("/admin/vouchers")
public class AdminVoucher extends HttpServlet {

    private CouponService couponService = new CouponService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "create":
                request.getRequestDispatcher("/WEB-INF/AdminViews/addVoucher.jsp")
                        .forward(request, response);
                break;

            case "edit":
                String id = request.getParameter("id");
                Coupon c = couponService.getCouponById(id);

                request.setAttribute("coupon", c);
                request.getRequestDispatcher("/WEB-INF/AdminViews/voucherUpdate.jsp")
                        .forward(request, response);
                break;

            case "delete":
                String deleteId = request.getParameter("id");
                couponService.deleteCoupon(deleteId);
                response.sendRedirect("vouchers");
                break;

            default:
                List<Coupon> list = couponService.getAllCoupons();
                request.setAttribute("coupons", list);
                request.getRequestDispatcher("/WEB-INF/AdminViews/voucher.jsp")
                        .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            String id = request.getParameter("id");
            String type = request.getParameter("type");
            int value = Integer.parseInt(request.getParameter("value"));
            int maxDiscount = Integer.parseInt(request.getParameter("maxDiscount"));
            int minCost = Integer.parseInt(request.getParameter("minCost"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            java.util.Date start = sdf.parse(request.getParameter("startedDate"));
            java.util.Date end = sdf.parse(request.getParameter("expiredDate"));

            boolean active = Boolean.parseBoolean(request.getParameter("active"));

            Coupon c = new Coupon();
            c.setId(id);
            c.setType(type);
            c.setValue(value);
            c.setMaxDiscount(maxDiscount);
            c.setMinCost(minCost);
            c.setStartedDate(start);
            c.setExpiredDate(end);
            c.setActive(active);

            if ("add".equals(action)) {
                couponService.addCoupon(c);
            } else {
                couponService.updateCoupon(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/vouchers");
    }
}