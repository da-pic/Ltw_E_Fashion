<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý đơn hàng</title>
</head>
<body>

<h2>Danh sách đơn hàng</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>User</th>
        <th>Tổng tiền</th>
        <th>Trạng thái</th>
        <th>Thanh toán</th>
        <th>Hành động</th>
    </tr>

    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>
            <td>${o.userId}</td>
            <td>${o.totalPrice}</td>
            <td>${o.status}</td>
            <td>${o.paymentMethod}</td>

            <td>
                <c:if test="${o.status == 'pending'}">

                    <!-- Xác nhận -->
                    <form action="order-action" method="post" style="display:inline;">
                        <input type="hidden" name="orderId" value="${o.id}" />
                        <input type="hidden" name="action" value="confirm" />
                        <button type="submit">Xác nhận</button>
                    </form>

                    <!-- Huỷ -->
                    <form action="order-action" method="post" style="display:inline;">
                        <input type="hidden" name="orderId" value="${o.id}" />
                        <input type="hidden" name="action" value="cancel" />
                        <button type="submit">Huỷ</button>
                    </form>

                </c:if>

                <c:if test="${o.status != 'pending'}">
                    Không thao tác
                </c:if>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>