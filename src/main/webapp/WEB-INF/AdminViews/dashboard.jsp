<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; }
        .sidebar { width: 250px; background: #343a40; color: white; height: 100vh; float: left; padding-top: 20px; }
        .sidebar a { color: #c2c7d0; text-decoration: none; display: block; padding: 10px 20px; }
        .sidebar a:hover { background: #494e53; color: white; }
        .main-content { margin-left: 250px; padding: 20px; }
        .card { background: white; padding: 20px; border-radius: 5px 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); display: inline-block; width: 30%; margin-right: 2%; margin-bottom: 20px;}
    </style>
</head>
<body>

    <div class="sidebar">
        <h3 style="text-align: center;">E-Fashion Admin</h3>
        <a href="${pageContext.request.contextPath}/admin/dashboard">🏠 Tổng quan</a>
        <a href="${pageContext.request.contextPath}/admin/users">👥 Quản lý Người dùng</a>
        <a href="${pageContext.request.contextPath}/admin/products">📦 Quản lý Sản phẩm</a>
        <a href="#orders">🛒 Quản lý Đơn hàng</a>
    </div>

    <div class="main-content">
        <h2>Xin chào Admin, ${sessionScope.currentUser.name}!</h2>
        <p>Chào mừng bạn đến với bảng điều khiển dành cho người quản trị.</p>

        <div class="card">
            <h3>Tổng số đơn hàng</h3>
            <h1>${totalOrders}</h1>
        </div>
        <div class="card">
            <h3>Tổng người dùng</h3>
            <h1>${totalUsers}</h1>
        </div>
        <div class="card">
            <h3>Tổng doanh thu</h3>
            <h1>${totalRevenue}</h1> 
        </div>
    </div>

</body>
</html>