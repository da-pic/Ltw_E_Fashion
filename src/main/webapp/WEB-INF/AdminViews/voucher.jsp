<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Voucher - Admin</title>

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
            font-weight: bold;
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

        .edit { color: #1976d2; }
        .delete { color: #d32f2f; }
    </style>
</head>

<body>

    <!-- HEADER -->
    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION</a>

            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
                <a href="${pageContext.request.contextPath}/admin/employees">NHÂN VIÊN</a>
                <a href="${pageContext.request.contextPath}/admin/orders">ĐƠN HÀNG</a>
                <a href="${pageContext.request.contextPath}/admin/vouchers" class="active">VOUCHER</a>
            </div>
        </div>

        <div class="user-profile">
            <span>👤 ${sessionScope.currentUser.name}</span>
            <span style="color: #ccc;">|</span>
            <a href="${pageContext.request.contextPath}/login" style="color: #666; text-decoration: none;">Đăng xuất</a>
        </div>
    </div>

    <!-- CONTENT -->
    <div class="container">

        <div class="page-header">
            <h2 class="page-title">QUẢN LÝ VOUCHER</h2>
            <a href="${pageContext.request.contextPath}/admin/vouchers?action=create" class="btn-add">
                + Thêm Voucher
            </a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Loại</th>
                    <th>Giá trị</th>
                    <th>Max Discount</th>
                    <th>Min Cost</th>
                    <th>Bắt đầu</th>
                    <th>Kết thúc</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="c" items="${coupons}">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.type}</td>
                        <td>${c.value}</td>
                        <td>${c.maxDiscount}</td>
                        <td>${c.minCost}</td>

                        <td>
                            <fmt:formatDate value="${c.startedDate}" pattern="dd/MM/yyyy"/>
                        </td>

                        <td>
                            <fmt:formatDate value="${c.expiredDate}" pattern="dd/MM/yyyy"/>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${c.active}">
                                    <span class="status-active">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-inactive">Inactive</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="action-links">
                            <a class="edit" href="${pageContext.request.contextPath}/admin/vouchers?action=edit&id=${c.id}">Sửa</a>
                            <a class="delete"
                               href="${pageContext.request.contextPath}/admin/vouchers?action=delete&id=${c.id}"
                               onclick="return confirm('Xóa voucher này?')">
                                Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty coupons}">
                    <tr>
                        <td colspan="9" style="text-align:center;">Chưa có voucher nào.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

    </div>

</body>
</html>