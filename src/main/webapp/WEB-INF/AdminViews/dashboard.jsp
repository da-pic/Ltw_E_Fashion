<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Quản Trị - Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

        .page-title {
            text-align: center;
            color: #e64a19; 
            text-transform: uppercase;
            margin-bottom: 30px;
            letter-spacing: 1px;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background-color: #ffffff;
            border: 1px solid #eaeaea;
            border-radius: 4px;
            padding: 25px;
            text-align: center;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05); 
            transition: transform 0.2s; 
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .stat-card h3 {
            margin: 0 0 15px 0;
            font-size: 16px;
            color: #333;
        }

        .stat-card p {
            margin: 0;
            font-size: 28px;
            font-weight: bold;
            color: #e64a19; 
        }
    </style>
</head>
<body>

    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
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
        <h2 class="page-title">TỔNG QUAN HỆ THỐNG</h2>
        
        <div class="dashboard-grid">
            <div class="stat-card">
                <h3>Tổng Sản Phẩm</h3>
                <p>${empty totalProducts ? '0' : totalProducts}</p>
            </div>
            <div class="stat-card">
                <h3>Người Dùng</h3>
                <p>${empty totalUsers ? '0' : totalUsers}</p>
            </div>
            <div class="stat-card">
                <h3>Đơn Hàng Chờ Duyệt</h3>
                <p>${empty pendingOrders ? '0' : pendingOrders}</p>
            </div>
            <div class="stat-card">
                <h3>Tổng Doanh Thu</h3>
                <p>${empty totalRevenue ? '0' : totalRevenue}</p>
            </div>
        </div>

        <div style="display: flex; gap: 20px; margin-top: 40px; flex-wrap: wrap;">
            
            <div style="flex: 1; min-width: 300px; background: #fff; padding: 25px; border-radius: 4px; border: 1px solid #eaeaea; box-shadow: 0 1px 3px rgba(0,0,0,0.05); text-align: center;">
                <h3 style="color: #333; margin-bottom: 20px;">TỶ LỆ TRẠNG THÁI ĐƠN HÀNG</h3>
                <div style="width: 100%; max-width: 350px; margin: 0 auto;">
                    <canvas id="orderChart"></canvas>
                </div>
            </div>

            <div style="flex: 2; min-width: 400px; background: #fff; padding: 25px; border-radius: 4px; border: 1px solid #eaeaea; box-shadow: 0 1px 3px rgba(0,0,0,0.05); text-align: center;">
                <h3 style="color: #333; margin-bottom: 20px;">SẢN PHẨM THEO DANH MỤC</h3>
                <div style="width: 100%; margin: 0 auto;">
                    <canvas id="categoryChart"></canvas>
                </div>
            </div>

        </div>

        <script>
            const dataDelivered = parseInt('${countDelivered}') || 0;
            const dataShipping  = parseInt('${countShipping}') || 0;
            const dataPending   = parseInt('${countPending}') || 0;
            const dataCanceled  = parseInt('${countCanceled}') || 0;

            const ctxOrder = document.getElementById('orderChart').getContext('2d');
            new Chart(ctxOrder, {
                type: 'doughnut', 
                data: {
                    labels: ['Hoàn thành', 'Đang giao', 'Chờ duyệt', 'Đã hủy'],
                    datasets: [{
                        data: [dataDelivered, dataShipping, dataPending, dataCanceled],
                        backgroundColor: ['#1cc88a', '#36b9cc', '#f6c23e', '#e74a3b'],
                        hoverOffset: 4
                    }]
                },
                options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
            });

            const ctxCategory = document.getElementById('categoryChart').getContext('2d');
            new Chart(ctxCategory, {
                type: 'bar', 
                data: {
                    labels: [
                        <c:choose>
                            <c:when test="${empty catLabels}">'Chưa có dữ liệu'</c:when>
                            <c:otherwise>${catLabels}</c:otherwise>
                        </c:choose>
                    ],
                    datasets: [{
                        label: 'Số lượng sản phẩm',
                        data: [
                            <c:choose>
                                <c:when test="${empty catValues}">0</c:when>
                                <c:otherwise>${catValues}</c:otherwise>
                            </c:choose>
                        ],
                        backgroundColor: '#4e73df', // Màu xanh lam
                        borderRadius: 4 
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true, ticks: { stepSize: 1 } } 
                    },
                    plugins: { legend: { display: false } } 
                }
            });
        </script>
    </div>

</body>
</html>