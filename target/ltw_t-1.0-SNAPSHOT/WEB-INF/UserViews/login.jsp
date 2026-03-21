<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Đăng nhập</title></head>
<body>
    <h2>Đăng nhập hệ thống</h2>
    <p style="color:red;">${error}</p>
    <p style="color:green;">${param.msg == 'success' ? 'Đăng ký thành công, vui lòng đăng nhập!' : ''}</p>
    
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Tên đăng nhập:</label><br>
        <input type="text" name="username" required><br><br>
        
        <label>Mật khẩu:</label><br>
        <input type="password" name="password" required><br><br>
        
        <button type="submit">Đăng nhập</button>
    </form>
    <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký ngay</a>
</body>
</html>