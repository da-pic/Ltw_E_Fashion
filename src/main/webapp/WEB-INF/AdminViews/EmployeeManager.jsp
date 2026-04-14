<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Nhân Viên - Admin</title>
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

        .btn-view {
            background-color: #1976d2;
            color: white;
            padding: 6px 10px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 13px;
            font-weight: bold;
        }

        .btn-view:hover {
            background-color: #0d47a1;
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
        .action-buttons {
            display: flex;
            gap: 8px;
        }
        .btn-salary { background-color: #1976d2; }
        .btn-edit   { background-color: #f57c00; }
        .btn-delete { background-color: #d32f2f; }

        .btn-salary:hover { background-color: #0d47a1; }
        .btn-edit:hover   { background-color: #e65100; }
        .btn-delete:hover { background-color: #b71c1c; }


    </style>
</head>

<body>

<div class="top-header">
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
            <a href="${pageContext.request.contextPath}/admin/products">SẢN PHẨM</a>
            <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
            <a href="${pageContext.request.contextPath}/admin/employees" class="active">NHÂN VIÊN</a>
            <a href="${pageContext.request.contextPath}/admin/orders">ĐƠN HÀNG</a>
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
        <h2 class="page-title">QUẢN LÝ NHÂN VIÊN</h2>
        <a href="${pageContext.request.contextPath}/admin/employees/add" class="btn-add">+ Thêm Nhân Viên</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>Mã NV</th>
                <th>Tên Nhân Viên</th>
                <th>Ngày Sinh</th>
                <th>Số Điện Thoại</th>
                <th>Lương Cơ Bản</th>
                <th>Hành Động</th>
            </tr>
        </thead>
        <tbody>

            <c:forEach var="e" items="${employeeList}">
                <tr>
                    <td>${e.employeeId}</td>
                    <td>${e.employeeName}</td>
                    <td>${e.employeeBirthDate}</td>
                    <td>${e.employeePhoneNumber}</td>
                    <td>${e.baseSalary}</td>
                    <td>  
                        <a href="${pageContext.request.contextPath}/admin/employees/salary?id=${e.employeeId}" 
                           class="btn-view btn-salary">
                           Xem
                        </a>
                           
                        <a href="${pageContext.request.contextPath}/admin/employees?action=delete&id=${e.employeeId}" 
                           class="btn-view btn-delete"
                           onclick="return confirm('Bạn có chắc muốn xóa nhân viên này không?');">
                           Xóa
                        </a>

                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty employeeList}">
                <tr>
                    <td colspan="5" style="text-align:center;">Chưa có nhân viên nào.</td>
                </tr>
            </c:if>

        </tbody>
    </table>
</div>

</body>
</html>