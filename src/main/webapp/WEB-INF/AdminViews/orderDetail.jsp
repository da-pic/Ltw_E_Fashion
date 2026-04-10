<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Đơn Hàng - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; color: #333; margin: 0;}
        .top-header { background-color: #ffffff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; align-items: center; }
        .nav-links a { text-decoration: none; color: #555; padding: 10px 15px; font-weight: bold; }
        .nav-links a.active { color: #e64a19; }
        
        .container { padding: 30px; max-width: 1000px; margin: 0 auto; }
        .card { background: white; padding: 20px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); margin-bottom: 20px; }
        .section-title { color: #1976d2; margin-top: 0; border-bottom: 2px solid #eee; padding-bottom: 10px; }
        
        .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; line-height: 1.6; }
        
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; }
        
        .status-form { display: flex; align-items: center; gap: 15px; background: #fff3e0; padding: 15px; border-radius: 4px; border: 1px solid #ffe0b2;}
        select { padding: 8px; border-radius: 4px; border: 1px solid #ccc; font-size: 15px; }
        .btn-update { background-color: #e64a19; color: white; padding: 8px 15px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-update:hover { background-color: #d84315; }
        .btn-back { display: inline-block; margin-top: 20px; text-decoration: none; color: #555; }
    </style>
</head>
<body>
    <div class="top-header">
        <div class="header-left">
            <span style="font-size: 20px; font-weight: bold; margin-right: 20px;">E-FASHION ADMIN</span>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
                <a href="${pageContext.request.contextPath}/admin/orders" class="active">ĐƠN HÀNG</a>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="card">
            <h3 class="section-title">THÔNG TIN KHÁCH HÀNG (Mã Đơn: ${order.id})</h3>
            <div class="info-grid">
                <div><strong>Người nhận:</strong> ${order.customerName}</div>
                <div><strong>Điện thoại:</strong> ${order.phone}</div>
                <div><strong>Địa chỉ giao:</strong> ${order.address}</div>
                <div><strong>Ngày đặt:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></div>
            </div>
        </div>

        <div class="card">
            <h3 class="section-title">CẬP NHẬT TRẠNG THÁI</h3>
            <form action="${pageContext.request.contextPath}/admin/orders/detail" method="POST" class="status-form">
                <input type="hidden" name="orderId" value="${order.id}">
                <label style="font-weight: bold;">Trạng thái hiện tại:</label>
                <select name="status">
                    <option value="Chờ xác nhận" ${order.status == 'Chờ xác nhận' ? 'selected' : ''}>Chờ xác nhận</option>
                    <option value="Đang giao" ${order.status == 'Đang giao' ? 'selected' : ''}>Đang giao hàng</option>
                    <option value="Hoàn thành" ${order.status == 'Hoàn thành' ? 'selected' : ''}>Hoàn thành (Đã nhận)</option>
                    <option value="Đã hủy" ${order.status == 'Đã hủy' ? 'selected' : ''}>Hủy đơn</option>
                </select>
                <button type="submit" class="btn-update">Lưu Trạng Thái</button>
            </form>
        </div>

        <div class="card">
            <h3 class="section-title">CHI TIẾT SẢN PHẨM MUA</h3>
            <table>
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Phân loại (Màu - Size)</th>
                        <th>Đơn giá</th>
                        <th>SL</th>
                        <th>Thành tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${order.orderDetails}">
                        <tr>
                            <td style="font-weight: bold;">${item.productName}</td>
                            <td>${item.color} - Size ${item.size}</td>
                            <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> đ</td>
                            <td>${item.quantity}</td>
                            <td style="color: #e64a19; font-weight: bold;">
                                <fmt:formatNumber value="${item.price * item.quantity}" pattern="#,###"/> đ
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4" style="text-align: right; font-size: 18px; font-weight: bold;">TỔNG CỘNG:</td>
                        <td style="color: #d32f2f; font-size: 18px; font-weight: bold;">
                            <fmt:formatNumber value="${order.totalAmount}" pattern="#,###"/> đ
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <a href="${pageContext.request.contextPath}/admin/orders" class="btn-back">← Quay lại danh sách đơn hàng</a>
    </div>
</body>
</html>