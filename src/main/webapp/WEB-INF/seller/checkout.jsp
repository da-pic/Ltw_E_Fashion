<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán đơn hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background: #f7f7f7;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            display: flex;
            gap: 20px;
        }

        .left, .right {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
        }

        .left {
            flex: 2;
        }

        .right {
            flex: 1;
            height: fit-content;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table th, table td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        .error {
            color: red;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .block {
            margin-bottom: 20px;
        }

        select, button {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin: 8px 0;
        }

        .total {
            font-size: 20px;
            font-weight: bold;
            color: #e53935;
        }

        .btn-order {
            background: #28a745;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 6px;
            font-size: 16px;
        }

        .btn-order:hover {
            background: #218838;
        }
    </style>
</head>
<body>

<h1>Thanh toán đơn hàng</h1>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<c:choose>
    <c:when test="${empty cartItems}">
        <p>Giỏ hàng đang trống.</p>
    </c:when>
    <c:otherwise>
        <form action="place-order" method="post">
            <div class="container">

                <div class="left">
                    <h2>Sản phẩm trong giỏ</h2>
                    <table>
                        <thead>
                        <tr>
                            <th>Tên sản phẩm</th>
                            <th>Phân loại</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Thành tiền</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td>${item.productName}</td>
                                <td>${item.color} - ${item.size}</td>
                                <td>${item.amount}</td>
                                <td>
                                    <fmt:formatNumber value="${item.unitPrice}" type="number"/> đ
                                </td>
                                <td>
                                    <fmt:formatNumber value="${item.lineTotal}" type="number"/> đ
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="right">
                    <div class="block">
                        <h3>Địa chỉ giao hàng</h3>
                        <select name="addressId" required>
                            <option value="">-- Chọn địa chỉ --</option>
                            <c:forEach var="a" items="${addresses}">
                                <option value="${a.id}">
                                    ${a.fullAddress} - ${a.phoneNumber}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="block">
                        <h3>Phương thức thanh toán</h3>
                        <select name="paymentMethod" required>
                            <option value="cash">Thanh toán khi nhận hàng (COD)</option>
                            <option value="transfer">Chuyển khoản</option>
                        </select>
                    </div>

                    <div class="block">
                        <h3>Mã giảm giá</h3>
                        <select name="couponId">
                            <option value="">-- Không sử dụng --</option>
                            <c:forEach var="c" items="${coupons}">
                                <option value="${c.id}">
                                    ${c.id} - ${c.type} - value: ${c.value}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="block">
                        <h3>Tóm tắt đơn hàng</h3>
                        <div class="summary-row">
                            <span>Tạm tính:</span>
                            <span><fmt:formatNumber value="${summary.subtotal}" type="number"/> đ</span>
                        </div>
                        <div class="summary-row">
                            <span>Phí ship:</span>
                            <span><fmt:formatNumber value="${summary.shippingFee}" type="number"/> đ</span>
                        </div>
                        <div class="summary-row">
                            <span>Giảm giá:</span>
                            <span><fmt:formatNumber value="${summary.discountAmount}" type="number"/> đ</span>
                        </div>
                        <hr>
                        <div class="summary-row total">
                            <span>Tổng thanh toán:</span>
                            <span><fmt:formatNumber value="${summary.finalTotal}" type="number"/> đ</span>
                        </div>
                    </div>

                    <button type="submit" class="btn-order">Đặt hàng</button>
                </div>

            </div>
        </form>
    </c:otherwise>
</c:choose>

</body>
</html>