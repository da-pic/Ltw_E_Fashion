<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; }
        .sidebar { width: 250px; background: #343a40; color: white; height: 100vh; float: left; padding-top: 20px; position: fixed;}
        .sidebar a { color: #c2c7d0; text-decoration: none; display: block; padding: 10px 20px; }
        .sidebar a:hover { background: #494e53; color: white; }
        .main-content { margin-left: 250px; padding: 20px; }
        
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #343a40; color: white; }
        tr:hover { background-color: #f1f1f1; }
        
        .btn { padding: 6px 12px; text-decoration: none; color: white; border-radius: 4px; font-size: 14px; }
        .btn-add { background-color: #28a745; margin-bottom: 15px; display: inline-block; font-size: 16px; font-weight: bold;}
        .btn-edit { background-color: #ffc107; color: #000; }
        .btn-delete { background-color: #dc3545; }
        .btn-variant { background-color: #17a2b8; } /* Nút xem các biến thể (Màu/Size) */
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
        <h2>Danh sách Sản phẩm E-Fashion</h2>
        
        <a href="${pageContext.request.contextPath}/admin/products/add" class="btn btn-add">➕ Nhập thêm sản phẩm mới</a>
        
        <table>
            <thead>
                <tr>
                    <th>Mã SP</th>
                    <th>Tên Sản phẩm</th>
                    <th>Thương hiệu</th>
                    <th>Danh mục</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Product> productList = (List<Product>) request.getAttribute("listProducts");
                    if(productList != null && !productList.isEmpty()) {
                        for(Product p : productList) { 
                %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><b><%= p.getProduct_name() %></b></td>
                        <td><%= p.getBrandName() != null ? p.getBrandName() : "Không có" %></td>
                        <td><%= p.getCategory() != null ? p.getCategory() : "Không có" %></td>
                        <td>
                            <span style="color: <%= p.isActive() ? "green" : "red" %>;">
                                <%= p.isActive() ? "Đang bán" : "Ngừng bán" %>
                            </span>
                        </td>
                        <td>
                            <a href="#" class="btn btn-variant">Màu/Size</a>
                            <a href="#" class="btn btn-edit">Sửa</a>
                            <a href="#" class="btn btn-delete">Ẩn</a>
                        </td>
                    </tr>
                <% 
                        }
                    } else {
                %>
                    <tr><td colspan="6" style="text-align: center;">Không có dữ liệu sản phẩm</td></tr>
                <%  } %>
            </tbody>
        </table>
    </div>

</body>
</html>