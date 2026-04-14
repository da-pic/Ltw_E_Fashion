<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Sản Phẩm Toàn Diện - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f6f9; color: #333; }
        .top-header { background-color: #ffffff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header-left { display: flex; align-items: center; gap: 20px; }
        .brand { font-size: 20px; font-weight: bold; color: #333; text-decoration: none; padding-right: 20px; border-right: 2px solid #eee; }
        .nav-links a { text-decoration: none; color: #555; padding: 10px 15px; font-weight: bold; }
        .nav-links a.active { color: #e64a19; }
        .user-profile { font-weight: bold; display: flex; align-items: center; gap: 8px; }
        
        .container { padding: 30px; max-width: 1100px; margin: 0 auto; }
        .page-title { color: #e64a19; text-transform: uppercase; margin-bottom: 20px; text-align: center;}
        
        .form-card { background: white; padding: 30px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; font-weight: bold; margin-bottom: 8px; }
        .form-control { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-family: Arial, sans-serif;}
        .form-control:focus { border-color: #e64a19; outline: none; }
        .readonly-input { background-color: #e9ecef; color: #666; cursor: not-allowed; }
        
        .btn-submit { background-color: #1976d2; color: white; border: none; padding: 12px 20px; font-size: 16px; font-weight: bold; border-radius: 4px; cursor: pointer; width: 100%; margin-top: 20px;}
        .btn-submit:hover { background-color: #1565c0; }
        .btn-add-row { background-color: #4caf50; color: white; border: none; padding: 8px 15px; font-weight: bold; border-radius: 4px; cursor: pointer; margin-bottom: 15px;}
        .btn-remove-row { background-color: #d32f2f; color: white; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer;}
        .btn-back { display: block; text-align: center; margin-top: 15px; color: #555; text-decoration: none; }
        
        .variant-table { width: 100%; border-collapse: collapse; margin-bottom: 15px; }
        .variant-table th { background-color: #f8f9fa; padding: 10px; border: 1px solid #ddd; text-align: left; }
        .variant-table td { padding: 8px; border: 1px solid #ddd; }
        .section-title { margin-top: 30px; color: #1976d2; border-bottom: 2px solid #eee; padding-bottom: 10px; font-size: 18px;}
        
        .category-cascade { display: flex; gap: 10px; flex-wrap: wrap; }
        .category-cascade select { flex: 1; min-width: 150px; }
    </style>
</head>
<body>
    <div class="top-header">
        <div class="header-left">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION ADMIN</a>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">TỔNG QUAN</a>
                <a href="${pageContext.request.contextPath}/admin/products" class="active">SẢN PHẨM</a>
            </div>
        </div>
        <div class="user-profile">
            <span>👤 ${sessionScope.currentUser.name}</span>
        </div>
    </div>

    <div class="container">
        <div class="form-card">
            <h2 class="page-title" style="color: #1976d2;">CẬP NHẬT CHI TIẾT SẢN PHẨM</h2>
            
            <c:if test="${not empty error}">
                <div style="color: white; background-color: #d32f2f; padding: 10px; margin-bottom: 20px; border-radius: 4px; text-align: center;">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/products/update" method="POST">
                <div class="form-group">
                    <label>Mã Sản Phẩm (ID)</label>
                    <input type="text" name="productId" class="form-control readonly-input" value="${product.id}" readonly>
                </div>
                
                <div class="form-group">
                    <label>Tên Sản Phẩm</label>
                    <input type="text" name="productName" class="form-control" value="${product.product_name}" required>
                </div>

                <div style="display: flex; gap: 20px;">
                    <div class="form-group" style="flex: 1;">
                        <label>Thương Hiệu</label>
                        <select name="brandId" class="form-control" required>
                            <c:forEach var="b" items="${brands}">
                                <option value="${b.id}" ${b.id == product.brand_id ? 'selected' : ''}>${b.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group" style="flex: 1;">
                        <label>Nhà Cung Cấp</label>
                        <select name="supplierId" class="form-control">
                            <option value="">-- Chọn Nhà cung cấp --</option>
                            <c:forEach var="s" items="${suppliers}">
                                <option value="${s.supplierId}" ${s.supplierId == product.supplierID ? 'selected' : ''}>${s.supplierName}</option>                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label>Danh Mục Phân Cấp</label>
                    <div id="categoryContainer" class="category-cascade"></div>
                    <input type="hidden" name="categoryId" id="finalCategoryId" value="${product.category_id}">
                </div>

                <div class="form-group">
                    <label>Trạng Thái</label>
                    <select name="isActive" class="form-control">
                        <option value="true" ${product.active ? 'selected' : ''}>Hoạt động</option>
                        <option value="false" ${!product.active ? 'selected' : ''}>Đã ẩn</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Mô tả chung</label>
                    <textarea name="description" class="form-control" rows="3">${product.description}</textarea>
                </div>

                <h3 class="section-title">THÔNG TIN PHÂN LOẠI (Giá, Size, Màu)</h3>
                <button type="button" class="btn-add-row" onclick="addVariantRow()">+ Thêm phân loại mới</button>
                
                <table class="variant-table" id="variantTable">
                    <thead>
                        <tr>
                            <th style="width: 15%;">Mã (ID)</th>
                            <th>Màu sắc</th>
                            <th>Kích cỡ</th>
                            <th>Giá bán (VNĐ)</th>
                            <th>Tồn kho</th>
                            <th style="width: 8%; text-align: center;">Xóa</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${product.productVariants}">
                            <tr>
                                <td><input type="text" name="varId" value="${v.id}" class="form-control readonly-input" readonly></td>
                                <td><input type="text" name="varColor" value="${v.color}" class="form-control" required></td>
                                <td><input type="text" name="varSize" value="${v.size}" class="form-control" required></td>
                                <td><input type="number" name="varPrice" value="${v.price}" class="form-control" required min="0"></td>
                                <td><input type="number" name="varStock" value="${v.stock}" class="form-control" required min="0"></td>
                                <td style="text-align: center;"><button type="button" class="btn-remove-row" onclick="removeRow(this)">X</button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <button type="submit" class="btn-submit">LƯU TOÀN BỘ THAY ĐỔI</button>
            </form>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">← Quay lại danh sách</a>
        </div>
    </div>

    <script>
        const categories = [
            <c:forEach var="c" items="${categories}" varStatus="loop">
                { id: ${c.id}, parentId: ${empty c.parentId ? 'null' : c.parentId}, name: '${c.name}' }${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        const initialCatId = parseInt(document.getElementById('finalCategoryId').value) || null;
        const container = document.getElementById('categoryContainer');
        const finalInput = document.getElementById('finalCategoryId');

        let path = [];
        let currentCat = categories.find(c => c.id === initialCatId);
        while (currentCat) {
            path.unshift(currentCat);
            currentCat = categories.find(c => c.id === currentCat.parentId);
        }

        function renderDropdowns() {
            container.innerHTML = '';
            let currentParentId = null;

            for (let i = 0; i <= path.length; i++) {
                const children = categories.filter(c => c.parentId === currentParentId);
                if (children.length === 0) break;

                const select = document.createElement('select');
                select.className = 'form-control';
                select.dataset.level = i;
                select.innerHTML = '<option value="">-- Chọn danh mục --</option>';

                let selectedId = path[i] ? path[i].id : null;
                children.forEach(c => {
                    const opt = document.createElement('option');
                    opt.value = c.id;
                    opt.textContent = c.name;
                    if (c.id === selectedId) opt.selected = true;
                    select.appendChild(opt);
                });

                select.addEventListener('change', handleSelectChange);
                container.appendChild(select);

                if (selectedId) currentParentId = selectedId;
                else break;
            }
        }

        function handleSelectChange(event) {
            const level = parseInt(event.target.dataset.level);
            const selectedId = event.target.value ? parseInt(event.target.value) : null;
            path = path.slice(0, level);
            if (selectedId) {
                const cat = categories.find(c => c.id === selectedId);
                if(cat) path.push(cat);
            }
            updateFinal();
            renderDropdowns();
        }

        function updateFinal() {
            finalInput.value = path.length > 0 ? path[path.length - 1].id : '';
        }

        function addVariantRow() {
            const tbody = document.querySelector('#variantTable tbody');
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><input type=\"text\" name=\"varId\" value=\"\" class=\"form-control readonly-input\" placeholder=\"Tự cấp mã\" readonly></td>
                <td><input type=\"text\" name=\"varColor\" class=\"form-control\" required></td>
                <td><input type=\"text\" name=\"varSize\" class=\"form-control\" required></td>
                <td><input type=\"number\" name=\"varPrice\" class=\"form-control\" required min=\"0\"></td>
                <td><input type=\"number\" name=\"varStock\" class=\"form-control\" required min=\"0\" value=\"0\"></td>
                <td style=\"text-align: center;\"><button type=\"button\" class=\"btn-remove-row\" onclick=\"removeRow(this)\">X</button></td>
            `;
            tbody.appendChild(tr);
        }

        function removeRow(btn) {
            if(confirm('Xóa dòng này?')) btn.closest('tr').remove();
        }

        window.addEventListener('DOMContentLoaded', renderDropdowns);
    </script>
</body>
</html>