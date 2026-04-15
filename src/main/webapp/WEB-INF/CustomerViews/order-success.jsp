<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt Hàng Thành Công</title>
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .success-box { background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; max-width: 500px; }
        .icon { font-size: 60px; color: #4CAF50; margin-bottom: 20px; }
        .title { color: #ee4d2d; font-size: 24px; margin-bottom: 10px; }
        .message { color: #555; margin-bottom: 30px; line-height: 1.5; }
        .btn-group { display: flex; gap: 15px; justify-content: center; }
        .btn { padding: 12px 25px; text-decoration: none; border-radius: 4px; font-weight: bold; transition: 0.2s; }
        .btn-home { border: 1px solid #ee4d2d; color: #ee4d2d; }
        .btn-home:hover { background: #fff5f5; }
        .btn-history { background: #ee4d2d; color: #fff; }
        .btn-history:hover { background: #d73211; }
    </style>
</head>
<body>
    <div class="success-box">
        <div class="icon">✅</div>
        <h2 class="title">ĐẶT HÀNG THÀNH CÔNG!</h2>
        <p class="message">Cảm ơn bạn đã mua sắm tại E-Fashion. Đơn hàng của bạn đã được ghi nhận và đang chờ xử lý. Chúng tôi sẽ sớm liên hệ với bạn để giao hàng.</p>
        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/CustomerHome" class="btn btn-home">Quay lại Trang Chủ</a>
            <a href="${pageContext.request.contextPath}/order-history" class="btn btn-history">Xem Đơn Hàng</a>
        </div>
    </div>
</body>
</html>