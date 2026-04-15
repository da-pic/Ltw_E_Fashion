<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Người Dùng - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f6f9; color: #333; }
        .top-header { background-color: #ffffff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header-left { display: flex; align-items: center; gap: 20px; }
        .brand { font-size: 20px; font-weight: bold; color: #333; text-decoration: none; padding-right: 20px; border-right: 2px solid #eee; }
        .nav-links a { text-decoration: none; color: #555; padding: 10px 15px; font-weight: bold; }
        .nav-links a.active { color: #e64a19; }
        .user-profile { font-weight: bold; display: flex; align-items: center; gap: 8px; }
        
        .container { padding: 30px; max-width: 1200px; margin: 0 auto; }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .page-title { color: #e64a19; text-transform: uppercase; letter-spacing: 1px; margin: 0; }
        
        table { width: 100%; border-collapse: collapse; background-color: white; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; color: #333; font-weight: bold; }
        
        .status-active { color: #2e7d32; font-weight: bold; background: #e8f5e9; padding: 4px 8px; border-radius: 4px; font-size: 13px;}
        .status-locked { color: #c62828; font-weight: bold; background: #ffebee; padding: 4px 8px; border-radius: 4px; font-size: 13px;}
        
        .btn-lock { background-color: #d32f2f; color: white; text-decoration: none; padding: 6px 12px; border-radius: 4px; font-size: 13px; font-weight: bold;}
        .btn-unlock { background-color: #2e7d32; color: white; text-decoration: none; padding: 6px 12px; border-radius: 4px; font-size: 13px; font-weight: bold;}
        .btn-lock:hover { background-color: #b71c1c; }
        .btn-unlock:hover { background-color: #1b5e20; }

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
    </style>
</head>
<body>

    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users" class="active">NGƯỜI DÙNG</a>
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
            <h2 class="page-title">QUẢN LÝ NGƯỜI DÙNG</h2>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Tài khoản</th>
                    <th>Họ và Tên</th>
                    <th>SĐT</th>
                    <th>Giới tính</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${userList}">
                    <tr>
                        <td style="font-weight: bold;">${u.username}</td>
                        <td>${u.name}</td>
                        <td>${u.phonenumber}</td>
                        <td>${u.gender}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.active}">
                                    <span class="status-active">● Đang hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-locked">● Bị khóa</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${u.username != sessionScope.currentUser.username}">
                                <c:choose>
                                    <c:when test="${u.active}">
                                        <a href="${pageContext.request.contextPath}/admin/users/toggle?id=${u.id}&status=false" class="btn-lock" onclick="return confirm('Bạn có chắc muốn Khóa tài khoản này?');">Khóa</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/admin/users/toggle?id=${u.id}&status=true" class="btn-unlock">Mở Khóa</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty userList}">
                    <tr><td colspan="6" style="text-align: center;">Chưa có người dùng nào.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

</body>
</html>