<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Đơn Hàng - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .top-header { background-color: #ffffff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header-left { display: flex; align-items: center; gap: 20px; }
        .brand { font-size: 20px; font-weight: bold; color: #333; text-decoration: none; padding-right: 20px; border-right: 2px solid #eee; }
        .nav-links a { text-decoration: none; color: #555; padding: 10px 15px; font-weight: bold; }
        .nav-links a.active { color: #e64a19; }
        
        .container { padding: 30px; max-width: 1200px; margin: 0 auto; }
        .page-title { color: #e64a19; text-transform: uppercase; margin-bottom: 20px; }
        
        table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; }
        
        .status-badge { padding: 5px 10px; border-radius: 20px; font-size: 13px; font-weight: bold; }
        .status-pending { background-color: #fff3cd; color: #856404; }
        .status-shipping { background-color: #cce5ff; color: #004085; }
        .status-completed { background-color: #d4edda; color: #155724; }
        .status-cancelled { background-color: #f8d7da; color: #721c24; }
        
        .btn-view { background-color: #1976d2; color: white; padding: 6px 12px; text-decoration: none; border-radius: 4px; font-size: 13px; font-weight: bold;}
        .btn-view:hover { background-color: #1565c0; }
    </style>
</head>
<body>
    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION ADMIN</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
                <a href="${pageContext.request.contextPath}/admin/orders" class="active">ĐƠN HÀNG</a>
            </div>
        </div>
    </div>

    <div class="container">
        <h2 class="page-title">QUẢN LÝ ĐƠN HÀNG</h2>
        <table>
            <thead>
                <tr>
                    <th>Mã Đơn</th>
                    <th>Khách Hàng</th>
                    <th>Điện Thoại</th>
                    <th>Ngày Đặt</th>
                    <th>Tổng Tiền</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orderList}">
                    <tr>
                        <td style="font-weight: bold;">${o.id}</td>
                        <td>${o.customerName}</td>
                        <td>${o.phone}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty o.orderDate}">
                                    <fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
                                </c:when>
                                <c:otherwise>
                                    Chưa cập nhật
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="color: #e64a19; font-weight: bold;">
                            <fmt:formatNumber value="${o.totalAmount}" pattern="#,###"/> đ
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${o.status == 'Chờ xác nhận'}"><span class="status-badge status-pending">${o.status}</span></c:when>
                                <c:when test="${o.status == 'Đang giao'}"><span class="status-badge status-shipping">${o.status}</span></c:when>
                                <c:when test="${o.status == 'Hoàn thành'}"><span class="status-badge status-completed">${o.status}</span></c:when>
                                <c:when test="${o.status == 'Đã hủy'}"><span class="status-badge status-cancelled">${o.status}</span></c:when>
                                <c:otherwise><span class="status-badge status-pending">${o.status}</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/orders/detail?id=${o.id}" class="btn-view">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty orderList}">
                    <tr><td colspan="7" style="text-align: center;">Chưa có đơn hàng nào.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>