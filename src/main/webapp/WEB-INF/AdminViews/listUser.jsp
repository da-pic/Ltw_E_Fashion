<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
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
        
        /* CSS cho Bảng */
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #343a40; color: white; }
        tr:hover { background-color: #f1f1f1; }
        
        /* CSS cho Nút */
        .btn { padding: 6px 12px; text-decoration: none; color: white; border-radius: 4px; font-size: 14px; }
        .btn-edit { background-color: #ffc107; color: #000; }
        .btn-delete { background-color: #dc3545; }
        .badge-admin { color: red; font-weight: bold; }
        .badge-staff { color: blue; font-weight: bold; }
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
        <h2>Danh sách Tài khoản Hệ thống</h2>
        
        <table>
            <thead>
                <tr>
                    <th>Họ và Tên</th>
                    <th>Tên đăng nhập</th>
                    <th>Số ĐT</th>
                    <th>Vai trò</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Lấy dữ liệu từ Controller truyền sang
                    List<User> userList = (List<User>) request.getAttribute("listUsers");
                    if(userList != null && !userList.isEmpty()) {
                        for(User u : userList) { 
                %>
                    <tr>
                        <td><b><%= u.getName() %></b></td>
                        <td><%= u.getUsername() %></td>
                        <td><%= u.getPhonenumber() != null ? u.getPhonenumber() : "Trống" %></td>
                        <td>
                            <% if("admin".equals(u.getRole())) { %>
                                <span class="badge-admin">Admin</span>
                            <% } else if("staff".equals(u.getRole())) { %>
                                <span class="badge-staff">Nhân viên</span>
                            <% } else { %>
                                Khách hàng
                            <% } %>
                        </td>
                        <td>
                            <span style="color: <%= u.isActive() ? "green" : "gray" %>;">
                                <%= u.isActive() ? "Hoạt động" : "Bị khóa" %>
                            </span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=<%= u.getId() %>" class="btn btn-edit">Sửa</a>
                            
                            <% if (!"admin".equals(u.getRole())) { %>
                                <% if (u.isActive()) { %>
                                    <a href="${pageContext.request.contextPath}/admin/users?action=toggle&id=<%= u.getId() %>&status=true" 
                                       class="btn btn-delete" 
                                       onclick="return confirm('Bạn có chắc chắn muốn KHÓA tài khoản <%= u.getUsername() %> không?');">Khóa</a>
                                <% } else { %>
                                    <a href="${pageContext.request.contextPath}/admin/users?action=toggle&id=<%= u.getId() %>&status=false" 
                                       class="btn" style="background-color: #28a745; color: white;"
                                       onclick="return confirm('Bạn muốn MỞ KHÓA cho tài khoản <%= u.getUsername() %>?');">Mở khóa</a>
                                <% } %>
                                
                            <% } else { %>
                                <span style="color: #999; font-size: 13px; font-style: italic; margin-left: 8px;">(Không thể khóa)</span>
                            <% } %>
                        </td>
                    </tr>
                <% 
                        }
                    } else {
                %>
                    <tr><td colspan="6" style="text-align: center;">Không có dữ liệu người dùng</td></tr>
                <%  } %>
            </tbody>
        </table>
    </div>

</body>
</html>