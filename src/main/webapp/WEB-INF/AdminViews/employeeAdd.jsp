<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Người Dùng - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f6f9; color: #333; }
        .top-header { background-color: #ffffff; border-bottom: 1px solid #ddd; padding: 10px 30px; display: flex; justify-content: space-between; align-items: center; }
        .brand { font-size: 20px; font-weight: bold; color: #333; text-decoration: none; }
        
        .container { padding: 30px; max-width: 600px; margin: 40px auto; background: white; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .page-title { color: #e64a19; text-transform: uppercase; margin-bottom: 20px; text-align: center;}
        
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; font-weight: bold; margin-bottom: 5px; }
        .form-control { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .form-control:focus { border-color: #e64a19; outline: none; }
        
        .btn-submit { background-color: #2e7d32; color: white; border: none; padding: 12px; width: 100%; font-size: 16px; font-weight: bold; border-radius: 4px; cursor: pointer; margin-top: 10px;}
        .btn-submit:hover { background-color: #1b5e20; }
        .btn-back { display: block; text-align: center; margin-top: 15px; color: #555; text-decoration: none; }
        .error-msg { background-color: #ffebee; color: #c62828; padding: 10px; text-align: center; border-radius: 4px; margin-bottom: 15px; font-weight: bold;}
    </style>
</head>
<body>

    <div class="top-header">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">E-FASHION</a>
    </div>

    <div class="container">
        <h2 class="page-title">THÊM NGƯỜI DÙNG MỚI</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-msg"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/admin/employees/add" method="POST">
            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="name" class="form-control" required placeholder="Nhập họ và tên...">
            </div>
            
            <div class="form-group">
                <label>Tên đăng nhập (Username)</label>
                <input type="text" name="username" class="form-control" required placeholder="Viết liền không dấu...">
            </div>

            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" class="form-control" required placeholder="Nhập mật khẩu...">
            </div>

            <div style="display: flex; gap: 15px;">
                <div class="form-group" style="flex: 1;">
                    <label>Số điện thoại</label>
                    <input type="text" name="phonenumber" class="form-control" required>
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>Ngày sinh</label>
                    <input type="date" name="birthdate" class="form-control" required>
                </div>
            </div>

            <div style="display: flex; gap: 15px;">
                <div class="form-group" style="flex: 1;">
                    <label>Giới tính</label>
                    <select name="gender" class="form-control" required>
                        <option value="Male">Nam</option>
                        <option value="Female">Nữ</option>
                    </select>
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>Vai trò (Role)</label>
                    <select name="role" class="form-control" required>
                        <option value="staff">Nhân viên</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn-submit">TẠO TÀI KHOẢN</button>
            <a href="${pageContext.request.contextPath}/admin/employees" class="btn-back">← Quay lại danh sách</a>
        </form>
    </div>

</body>
</html>