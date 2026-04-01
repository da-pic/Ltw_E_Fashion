<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt hàng thành công</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4fff6;
            padding: 40px;
            text-align: center;
        }

        .box {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 10px #ddd;
        }

        .success {
            color: #28a745;
            font-size: 28px;
            font-weight: bold;
        }

        .info {
            color: #555;
            font-size: 18px;
            margin-top: 20px;
        }

        .order-id {
            margin-top: 20px;
            font-size: 18px;
        }

        .total {
            color: #e53935;
            font-size: 22px;
            font-weight: bold;
            margin-top: 15px;
        }

        a {
            display: inline-block;
            margin-top: 25px;
            text-decoration: none;
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border-radius: 6px;
        }
    </style>
</head>
<body>
<div class="box">

    <c:choose>
        <%-- Trường hợp được forward từ PlaceOrderServlet sau khi đặt hàng --%>
        <c:when test="${not empty orderId}">
            <div class="success">Đặt hàng thành công!</div>

            <div class="order-id">
                Mã đơn hàng: <strong>${orderId}</strong>
            </div>

            <div class="total">
                Tổng thanh toán:
                <fmt:formatNumber value="${finalTotal}" type="number"/> đ
            </div>
        </c:when>

        <%-- Trường hợp vào thẳng từ dashboard --%>
        <c:otherwise>
            <div class="info">Chưa có đơn hàng nào vừa được đặt.</div>
            <p style="color:#888; margin-top:10px;">Vui lòng tiến hành thanh toán để xem kết quả.</p>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/home">Tiếp tục mua sắm</a>
</div>
</body>
</html>
