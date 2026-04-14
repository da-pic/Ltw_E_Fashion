<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Đăng ký</title></head>
<body>
    <h2>Đăng ký tài khoản</h2>
    <p style="color:red;">${error}</p>
    
    <form action="${pageContext.request.contextPath}/register" method="post">
        <label>Họ và tên:</label><br>
        <input type="text" name="name" required><br><br>

        <label>Ngày sinh:</label><br>
        <input type="date" name="birthdate" required><br><br>

        <label>Số điện thoại:</label><br>
        <input type="text" name="phonenumber" required><br><br>

        <label>Giới tính:</label><br>
        <select name="gender">
            <option value="Male">Nam</option>
            <option value="Female">Nữ</option>
        </select><br><br>

        <label>Tên đăng nhập:</label><br>
        <input type="text" name="username" required><br><br>
        
        <label>Mật khẩu:</label><br>
        <input type="password" name="password" required><br><br>
        
        <button type="submit">Đăng ký</button>
    </form>
    <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
</body>
</html>