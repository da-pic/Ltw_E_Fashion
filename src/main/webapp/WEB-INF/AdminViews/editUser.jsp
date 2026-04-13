<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; }
        .sidebar { width: 250px; background: #343a40; color: white; height: 100vh; float: left; padding-top: 20px; position: fixed;}
        .sidebar a { color: #c2c7d0; text-decoration: none; display: block; padding: 10px 20px; }
        .main-content { margin-left: 250px; padding: 20px; }
        
        /* CSS cho Form */
        .form-container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); width: 50%; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input, .form-group select { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-submit { background-color: #007bff; color: white; border: none; padding: 10px 20px; cursor: pointer; border-radius: 4px; font-size: 16px; }
        .btn-submit:hover { background-color: #0056b3; }
        .btn-cancel { background-color: #6c757d; color: white; text-decoration: none; padding: 10px 20px; border-radius: 4px; font-size: 16px; margin-left: 10px; }
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
        <h2>Cập nhật thông tin tài khoản</h2>
        
        <% User u = (User) request.getAttribute("editUser"); %>
        
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/admin/users?action=update" method="POST">
                
                <input type="hidden" name="id" value="<%= u.getId() %>">

                <div class="form-group">
                    <label>Tên đăng nhập (Chỉ xem)</label>
                    <input type="text" value="<%= u.getUsername() %>" disabled style="background-color: #e9ecef;">
                </div>

                <div class="form-group">
                    <label>Họ và Tên</label>
                    <input type="text" name="name" value="<%= u.getName() %>" required>
                </div>

                <div class="form-group">
                    <label>Ngày sinh</label>
                    <input type="date" name="birthdate" value="<%= u.getBirthdate() %>" required>
                </div>

                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="text" name="phonenumber" value="<%= u.getPhonenumber() != null ? u.getPhonenumber() : "" %>">
                </div>

                <div class="form-group">
                    <label>Giới tính</label>
                    <select name="gender">
                        <option value="Male" <%= "Male".equals(u.getGender()) ? "selected" : "" %>>Nam</option>
                        <option value="Female" <%= "Female".equals(u.getGender()) ? "selected" : "" %>>Nữ</option>
                    </select>
                </div>

                <button type="submit" class="btn-submit">💾 Lưu cập nhật</button>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn-cancel">Hủy</a>
            </form>
        </div>
    </div>

</body>
</html>