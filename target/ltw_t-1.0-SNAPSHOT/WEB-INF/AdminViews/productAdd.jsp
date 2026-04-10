<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm Mới - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f6f9; color: #333; }
        .top-header { background-color: #ffffff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header-left { display: flex; align-items: center; gap: 20px; }
        .brand { font-size: 20px; font-weight: bold; color: #333; text-decoration: none; padding-right: 20px; border-right: 2px solid #eee; }
        .nav-links a { text-decoration: none; color: #555; padding: 10px 15px; font-weight: bold; }
        .nav-links a.active { color: #e64a19; }
        .user-profile { font-weight: bold; display: flex; align-items: center; gap: 8px; }
        
        .container { padding: 30px; max-width: 800px; margin: 0 auto; }
        .page-title { color: #e64a19; text-transform: uppercase; margin-bottom: 20px; text-align: center;}
        
        .form-card { background: white; padding: 30px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; font-weight: bold; margin-bottom: 8px; }
        .form-control { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-family: Arial, sans-serif;}
        .form-control:focus { border-color: #e64a19; outline: none; }
        
        .btn-submit { background-color: #e64a19; color: white; border: none; padding: 12px 20px; font-size: 16px; font-weight: bold; border-radius: 4px; cursor: pointer; width: 100%; }
        .btn-submit:hover { background-color: #d84315; }
        .btn-back { display: block; text-align: center; margin-top: 15px; color: #555; text-decoration: none; }
    </style>
</head>
<body>

    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION ADMIN</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products" class="active">SẢN PHẨM</a>
                <a href="${pageContext.request.contextPath}/admin/users">NGƯỜI DÙNG</a>
                <a href="${pageContext.request.contextPath}/admin/orders">ĐƠN HÀNG</a>
            </div>
        </div>
        <div class="user-profile">
            <span>👤 ${sessionScope.currentUser.name}</span>
        </div>
    </div>

    <div class="container">
        <div class="form-card">
            <h2 class="page-title">THÊM SẢN PHẨM MỚI</h2>
            
            <form action="${pageContext.request.contextPath}/admin/products/add" method="POST">
                <div class="form-group">
                    <label>Mã Sản Phẩm (ID)</label>
                    <input type="text" name="productId" class="form-control" placeholder="VD: prd-08" required>
                </div>
                
                <div class="form-group">
                    <label>Tên Sản Phẩm</label>
                    <input type="text" name="productName" class="form-control" placeholder="Nhập tên sản phẩm..." required>
                </div>

                <div class="form-group">
                    <label>Thương Hiệu</label>
                    <select name="brandId" class="form-control" required>
                        <option value="">-- Chọn thương hiệu --</option>
                        <c:forEach var="b" items="${brands}">
                            <option value="${b.brandId}">${b.brandName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Danh Mục</label>
                    <select name="categoryId" class="form-control" required>
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Mô tả sản phẩm</label>
                    <textarea name="description" class="form-control" rows="4" placeholder="Nhập mô tả chi tiết..."></textarea>
                </div>

                <button type="submit" class="btn-submit">LƯU SẢN PHẨM</button>
            </form>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">← Quay lại danh sách</a>
        </div>
    </div>

</body>
</html> 