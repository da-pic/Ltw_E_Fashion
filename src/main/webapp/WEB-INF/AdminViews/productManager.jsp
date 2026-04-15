<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Sản Phẩm - Admin</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 0; 
            padding: 0; 
            background-color: #f4f6f9; 
            color: #333;
        }

        .top-header {
            background-color: #ffffff;
            border-bottom: 1px solid #ddd;
            padding: 10px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header-left {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .brand {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            text-decoration: none;
            padding-right: 20px;
            border-right: 2px solid #eee;
        }

        .nav-links a {
            text-decoration: none;
            color: #555;
            padding: 10px 15px;
            font-weight: bold;
        }

        .nav-links a:hover, .nav-links a.active {
            color: #e64a19;
        }

        .user-profile {
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .container {
            padding: 30px;
            max-width: 1200px;
            margin: 0 auto;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .page-title {
            color: #e64a19;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin: 0;
        }

        .btn-add {
            background-color: #e64a19;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
        }

        .btn-add:hover {
            background-color: #d84315;
        }

        .btn-delete { 
            background-color: #d32f2f; 
            color: white; 
            padding: 6px 12px; 
            text-decoration: none; 
            border-radius: 4px; 
            font-size: 13px; 
            font-weight: bold;
            margin-left: 5px;
        }
        .btn-delete:hover { background-color: #b71c1c; }

        .btn-restore { 
            background-color: #2e7d32; /* Màu xanh lá */
            color: white; 
            padding: 6px 12px; 
            text-decoration: none; 
            border-radius: 4px; 
            font-size: 13px; 
            font-weight: bold;
            margin-left: 5px;
        }
        .btn-restore:hover { background-color: #1b5e20; }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f8f9fa;
            color: #333;
            font-weight: bold;
        }

        .img-thumbnail {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 4px;
        }

        .status-active {
            color: green;
            font-weight: bold;
        }
        .status-inactive {
            color: red;
            font-weight: bold;
        }

        .action-links a {
            text-decoration: none;
            margin-right: 10px;
        }
        .action-links .edit { color: #1976d2; }
        .action-links .delete { color: #d32f2f; }
    </style>
</head>
<body>

    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products" class="active">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
                <a href="${pageContext.request.contextPath}/admin/employees">NHÂN VIÊN</a>
                <a href="${pageContext.request.contextPath}/admin/orders">ĐƠN HÀNG</a>
                <a href="${pageContext.request.contextPath}/admin/vouchers">VOUCHER</a>
            </div>
        </div>
        <div class="user-profile">
            <span>👤 ${sessionScope.currentUser.name}</span>
            <span style="color: #ccc; margin: 0 10px;">|</span>
            <a href="${pageContext.request.contextPath}/login" style="color: #666; text-decoration: none; font-size: 14px;">Đăng xuất</a>
        </div>
    </div>

    <div class="container">
        <div class="page-header">
            <h2 class="page-title">QUẢN LÝ SẢN PHẨM</h2>
            <a href="${pageContext.request.contextPath}/admin/products/add" class="btn-add">+ Thêm Sản Phẩm Mới</a>        
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Ảnh</th>
                    <th>Mã SP</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Giá Hiện Thị</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${productList}">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}${p.display_image}" class="img-thumbnail" alt="Lỗi ảnh">
                        </td>
                        <td>${p.id}</td>
                        <td>${p.product_name}</td>
                        <td style="color: #e64a19; font-weight: bold;">
                            <c:choose>
                                <%-- Nếu chưa có biến thể nào (giá = 0) --%>
                                <c:when test="${empty p.display_price || p.display_price == 0}">
                                    <span style="color: #999; font-style: italic; font-weight: normal;">Chưa có giá</span>
                                </c:when>
                                
                                <%-- Nếu Giá Thấp Nhất KHÁC Giá Cao Nhất (Hiển thị khoảng giá) --%>
                                <c:otherwise>
                                    <fmt:formatNumber value="${p.display_price}" pattern="#,###"/> - <fmt:formatNumber value="${p.display_max_price}" pattern="#,###"/> VNĐ
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${p.active}">
                                    <span class="status-active">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-inactive">Đã ẩn</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="action-links">
                            <a href="${pageContext.request.contextPath}/admin/products/update?id=${p.id}" class="edit">Sửa</a>
                            <c:choose>
                                <c:when test="${p.active}">
                                    <a href="${pageContext.request.contextPath}/admin/products/delete?id=${p.id}" class="btn-delete" onclick="return confirm('Bạn có chắc chắn muốn ẨN sản phẩm này không?');">ẨN</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/admin/products/restore?id=${p.id}" class="btn-restore" onclick="return confirm('Bạn có chắc chắn muốn HIỆN sản phẩm này không?');">Hiện</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty productList}">
                    <tr>
                        <td colspan="6" style="text-align: center;">Chưa có sản phẩm nào.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>

</body>
</html>