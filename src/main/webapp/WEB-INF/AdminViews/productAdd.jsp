<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nhập Sản Phẩm Mới - Admin</title>
    <style>
        body { font-family: Arial; margin: 0; background: #f4f6f9; }

        .top-header {
            background: #fff;
            border-bottom: 1px solid #ddd;
            padding: 10px 30px;
            display: flex;
            justify-content: space-between;
        }

        .header-left {
            display: flex;
            gap: 20px;
            align-items: center;
        }

        .brand {
            font-size: 20px;
            font-weight: bold;
            text-decoration: none;
            color: #333;
        }

        .container {
            padding: 30px;
            max-width: 800px;
            margin: auto;
        }

        .form-card {
            background: white;
            padding: 30px;
            border-radius: 5px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .row {
            display: flex;
            gap: 10px;
        }

        .btn-submit {
            background: #e64a19;
            color: white;
            padding: 12px;
            border: none;
            width: 100%;
            cursor: pointer;
        }

        .btn-back {
            display: block;
            margin-top: 10px;
            text-align: center;
        }
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

        <form action="${pageContext.request.contextPath}/admin/products/add" method="POST" enctype="multipart/form-data">

            <!-- ID -->
            <div class="form-group">
                <label>Mã sản phẩm</label>
                <input type="text" name="productId" class="form-control" value="${productId}" required>
            </div>

            <!-- NAME -->
            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" name="productName" class="form-control" value="${productName}" required>
            </div>

            <!-- BRAND -->
            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" name="brandInput" class="form-control" value="${brandInput}">
            </div>

            <!-- SUPPLIER -->
            <div class="form-group">
                <label>Nhà cung cấp</label>
                <input type="text" name="supplierInput" class="form-control" value="${supplierInput}">
            </div>

            <!-- CATEGORY -->
            <div class="form-group">
                <label>Danh mục</label>

                <div class="row">

                    <select name="parentId" class="form-control" onchange="this.form.submit()">
                        <option value="">-- chọn --</option>
                        <c:forEach var="c" items="${parentCategories}">
                            <option value="${c.id}" ${c.id == selectedParent ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                    <select name="childId" class="form-control" onchange="this.form.submit()">
                        <option value="">-- chọn --</option>
                        <c:forEach var="c" items="${childCategories}">
                            <option value="${c.id}" ${c.id == selectedChild ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                    <select name="categoryId" class="form-control"">
                        <option value="">-- chọn --</option>
                        <c:forEach var="c" items="${grandChildCategories}">
                            <option value="${c.id}" ${c.id == selectedCategory ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                </div>
            </div>

            <!-- DESCRIPTION -->
            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description" class="form-control">${description}</textarea>
            </div>

            <h3 style="color: #1976d2; margin-top: 30px; border-bottom: 2px solid #eee; padding-bottom: 10px;">THÔNG TIN PHÂN LOẠI</h3>
            <button type="button" class="btn-submit" style="background-color: #4caf50; margin-bottom: 15px; width: auto;" onclick="addVariantRow()">+ Thêm phân loại</button>

            <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;" id="variantTable">
                <thead>
                    <tr style="background-color: #f8f9fa;">
                        <th style="padding: 10px; border: 1px solid #ddd; width: 15%; text-align: center;">Ảnh</th>
                        <th style="padding: 10px; border: 1px solid #ddd;">Màu sắc</th>
                        <th style="padding: 10px; border: 1px solid #ddd;">Kích cỡ</th>
                        <th style="padding: 10px; border: 1px solid #ddd;">Giá bán (VNĐ)</th>
                        <th style="padding: 10px; border: 1px solid #ddd;">Tồn kho</th>
                        <th style="padding: 10px; border: 1px solid #ddd; width: 8%; text-align: center;">Xóa</th>
                    </tr>
                </thead>
                <tbody>
                    </tbody>
            </table>

            <!-- SUBMIT -->
            <button type="submit" name="action" value="save" class="btn-submit">
                LƯU
            </button>

        </form>

        <c:if test="${not empty error}">
            <p style="color:red">${error}</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">
            ← Quay lại
        </a>

    </div>
</div>
<script>
    let rowCount = 0;
    
    function addVariantRow() {
        rowCount++;
        const tbody = document.querySelector('#variantTable tbody');
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td style="text-align: center; border: 1px solid #ddd; padding: 8px; vertical-align: middle;">
                <img id="preview_${rowCount}" src="" style="width: 50px; height: 50px; object-fit: cover; display: block; margin: 0 auto 5px; border-radius:4px;" alt="Ảnh">
                <input type="file" name="varImageFile" class="form-control" style="font-size: 11px; padding: 4px;" accept="image/*" onchange="previewImage(this, 'preview_${rowCount}')">
            </td>
            <td style="border: 1px solid #ddd; padding: 8px; vertical-align: middle;"><input type="text" name="varColor" class="form-control" required></td>
            <td style="border: 1px solid #ddd; padding: 8px; vertical-align: middle;"><input type="text" name="varSize" class="form-control" required></td>
            <td style="border: 1px solid #ddd; padding: 8px; vertical-align: middle;"><input type="number" name="varPrice" class="form-control" required min="0"></td>
            <td style="border: 1px solid #ddd; padding: 8px; vertical-align: middle;"><input type="number" name="varStock" class="form-control" required min="0" value="0"></td>
            <td style="text-align: center; border: 1px solid #ddd; padding: 8px; vertical-align: middle;"><button type="button" style="background: #d32f2f; color: white; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer;" onclick="this.closest('tr').remove()">X</button></td>
        `;
        tbody.appendChild(tr);
    }

    function previewImage(input, imgId) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) { document.getElementById(imgId).src = e.target.result; }
            reader.readAsDataURL(input.files[0]);
        }
    }

    window.addEventListener('DOMContentLoaded', () => { addVariantRow(); });
</script>
</body>
</html>