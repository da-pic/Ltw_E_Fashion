<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Checkout</title>
</head>
<body>

<h2>Tìm & thêm sản phẩm</h2>

<form method="get" action="checkout">
    <input type="text" name="keyword" placeholder="Tìm sản phẩm..."/>
    <button type="submit">Tìm</button>
</form>

<br/>

<table border="1">
    <tr>
        <th>Tên</th>
        <th>Hành động</th>
    </tr>

    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.productName}</td>
            <td>
                <form method="post">
                    <input type="hidden" name="action" value="add"/>
                    
                    <!-- 🔥 QUAN TRỌNG -->
                    <input type="hidden" name="productVariantId" value="${p.id}"/>
                    
                    <button>Thêm</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<hr/>

<h2>Sản phẩm trong hóa đơn</h2>

<table border="1">
    <tr>
        <th>Tên</th>
        <th>Giá</th>
        <th>SL</th>
        <th>Thành tiền</th>
        <th>Xóa</th>
    </tr>

    <c:forEach var="item" items="${cart}">
        <tr>
            <td>${item.productName}</td>
            <td>${item.unitPrice}</td>

            <td>
                <!-- giảm -->
                <form method="post" style="display:inline;">
                    <input type="hidden" name="action" value="decrease"/>
                    <input type="hidden" name="productVariantId" value="${item.productVariantId}"/>
                    <button>-</button>
                </form>

                ${item.amount}

                <!-- tăng -->
                <form method="post" style="display:inline;">
                    <input type="hidden" name="action" value="increase"/>
                    <input type="hidden" name="productVariantId" value="${item.productVariantId}"/>
                    <button>+</button>
                </form>
            </td>

            <td>${item.lineTotal}</td>

            <td>
                <form method="post">
                    <input type="hidden" name="action" value="remove"/>
                    <input type="hidden" name="productVariantId" value="${item.productVariantId}"/>
                    <button>X</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<hr/>

<h2>Tóm tắt</h2>

<p>Tạm tính: ${subtotal}</p>
<p>Ship: ${shipping}</p>
<p><b>Tổng: ${total}</b></p>

<br/>

<form method="post">
    <input type="hidden" name="action" value="checkout"/>
    <button>Đặt hàng</button>
</form>

</body>
</html>