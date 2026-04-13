<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nhập Sản Phẩm Mới - Admin</title>
    <style>
        body { font-family: Arial; margin: 0; background: #f4f6f9; }
        .top-header { background: #fff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; }
        .header-left { display: flex; gap: 20px; align-items: center; }
        .brand { font-size: 20px; font-weight: bold; text-decoration: none; color: #333; }
        .nav-links a { padding: 10px; text-decoration: none; color: #555; font-weight: bold; }
        .nav-links a.active { color: #e64a19; }

        .container { padding: 30px; max-width: 800px; margin: auto; }
        .form-card { background: white; padding: 30px; border-radius: 5px; }
        .form-group { margin-bottom: 20px; }
        .form-control { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-submit { background: #e64a19; color: white; padding: 12px; border: none; width: 100%; }
        .btn-back { display: block; margin-top: 10px; text-align: center; }
    </style>
</head>

<body>

<div class="top-header">
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">ADMIN</a>
    </div>
    <div>👤 ${sessionScope.currentUser.name}</div>
</div>

<div class="container">
    <div class="form-card">
        <h2 style="text-align:center; color:#e64a19;">NHẬP SẢN PHẨM MỚI</h2>

        <form action="${pageContext.request.contextPath}/admin/products/add" method="POST">

            <!-- ID -->
            <div class="form-group">
                <label>Mã sản phẩm</label>
                <input type="text" name="productId" class="form-control" required>
            </div>

            <!-- NAME -->
            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" name="productName" class="form-control" required>
            </div>

            <!-- BRAND -->
            <div class="form-group">
                <label>Thương hiệu</label>

                <select name="brandSelect" class="form-control">
                    <option value="">-- Chọn thương hiệu --</option>
                    <c:forEach var="b" items="${brands}">
                        <option value="${b.id}">${b.name}</option>
                    </c:forEach>
                </select>

                <input type="text" name="brandInput" class="form-control" 
                       placeholder="Hoặc nhập brand mới..." style="margin-top:8px;">
            </div>

            <!-- SUPPLIER -->
            <div class="form-group">
                <label>Nhà cung cấp</label>

                <select name="supplierSelect" class="form-control">
                    <option value="">-- Chọn supplier --</option>
                    <c:forEach var="s" items="${suppliers}">
                        <option value="${s.supplierId}">${s.supplierName}</option>
                    </c:forEach>
                </select>

                <input type="text" name="supplierInput" class="form-control" 
                       placeholder="Hoặc nhập supplier mới..." style="margin-top:8px;">
            </div>

            <!-- CATEGORY -->

            <!-- CATEGORY -->
            <div class="form-group">
                <label>Danh mục</label>

                <div style="display: flex; gap: 10px;">

                    <!-- CHA -->
                    <select name="parentId" class="form-control"
                            onchange="window.location.href='${pageContext.request.contextPath}/admin/products/add?parentId=' + this.value">
                        <option value="">-- Cha --</option>
                        <c:forEach var="c" items="${parentCategories}">
                            <option value="${c.id}" ${c.id == selectedParent ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                    <!-- CON -->
                    <select name="childId" class="form-control"
                            onchange="window.location.href='${pageContext.request.contextPath}/admin/products/add?parentId=${selectedParent}&childId=' + this.value">

                        <option value="">-- Con --</option>
                        <c:forEach var="c" items="${childCategories}">
                            <option value="${c.id}" ${c.id == selectedChild ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                    <!-- CHÁU -->
                    <select name="categoryId" class="form-control" required>
                        <option value="">-- Cháu --</option>
                        <c:forEach var="c" items="${grandChildCategories}">
                            <option value="${c.id}">
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                </div>
            </div>

            <!-- DESCRIPTION -->
            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description" class="form-control"></textarea>
            </div>

            <button type="submit" class="btn-submit">LƯU</button>
        </form>

        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">← Quay lại</a>
    </div>
</div>

</body>
</html>