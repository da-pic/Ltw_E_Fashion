<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa Sản phẩm</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f2f5; color: #333; }

        .container {
            max-width: 600px; margin: 50px auto; padding: 0 20px;
        }
        .card {
            background: #fff; border-radius: 12px;
            padding: 32px; box-shadow: 0 2px 16px rgba(0,0,0,0.09);
        }
        h2 { font-size: 22px; margin-bottom: 24px; color: #1a1a2e; }

        .form-group { margin-bottom: 18px; }
        label { display: block; font-size: 13px; font-weight: 600; margin-bottom: 6px; color: #555; }
        input[type="text"], textarea, select {
            width: 100%; padding: 10px 12px; border: 1px solid #d1d5db;
            border-radius: 6px; font-size: 14px; transition: border .2s;
        }
        input[type="text"]:focus, textarea:focus, select:focus {
            border-color: #4361ee; outline: none;
        }
        input[readonly] { background: #f3f4f6; color: #888; cursor: not-allowed; }
        textarea { resize: vertical; min-height: 80px; }

        .alert-error {
            padding: 10px 14px; background: #f8d7da; color: #721c24;
            border-radius: 6px; margin-bottom: 16px; font-size: 13px;
        }
        .btn-row { display: flex; gap: 12px; margin-top: 8px; }
        .btn {
            padding: 10px 22px; border-radius: 6px; font-size: 14px;
            font-weight: 600; border: none; cursor: pointer; text-decoration: none;
            transition: opacity .2s;
        }
        .btn:hover { opacity: .85; }
        .btn-save   { background: #4361ee; color: #fff; }
        .btn-cancel { background: #e9ecef; color: #333; }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <h2>✏️ Sửa Sản phẩm</h2>

        <c:if test="${not empty error}">
            <div class="alert-error">⚠️ ${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/seller/product">
            <input type="hidden" name="action" value="update"/>

            <div class="form-group">
                <label>ID Sản phẩm</label>
                <input type="text" name="id" value="${product.id}" readonly/>
            </div>

            <div class="form-group">
                <label>Tên sản phẩm <span style="color:red">*</span></label>
                <input type="text" name="productName" value="${product.productName}" required/>
            </div>

            <div class="form-group">
                <label>Thương hiệu (Brand ID)</label>
                <input type="text" name="brandId" value="${product.brandId}"/>
            </div>

            <div class="form-group">
                <label>Danh mục (Category ID)</label>
                <input type="text" name="categoryId" value="${product.categoryId}"/>
            </div>

            <div class="form-group">
                <label>Trạng thái</label>
                <select name="isActive">
                    <option value="1" ${product.isActive == 1 ? 'selected' : ''}>Hoạt động</option>
                    <option value="0" ${product.isActive == 0 ? 'selected' : ''}>Ẩn</option>
                </select>
            </div>

            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description">${product.description}</textarea>
            </div>

            <div class="btn-row">
                <button type="submit" class="btn btn-save">💾 Lưu thay đổi</button>
                <a href="${pageContext.request.contextPath}/seller/product" class="btn btn-cancel">Hủy</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
