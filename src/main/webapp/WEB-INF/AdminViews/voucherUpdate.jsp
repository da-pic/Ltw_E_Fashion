<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Voucher</title>

    <style>
        body { font-family: Arial; background: #f4f6f9; margin: 0; }

        .top-header {
            background: #fff;
            padding: 10px 30px;
            border-bottom: 1px solid #ddd;
        }

        .container {
            padding: 30px;
            max-width: 800px;
            margin: auto;
        }

        .form-box {
            background: white;
            padding: 20px;
            border-radius: 6px;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
        }

        .btn {
            background: #e64a19;
            color: white;
            padding: 10px;
            border: none;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="top-header">
    <a href="${pageContext.request.contextPath}/admin/vouchers">← Quay lại</a>
</div>

<div class="container">
    <h2>Cập nhật Voucher</h2>

    <div class="form-box">
        <form method="POST">

            <input type="hidden" name="id" value="${coupon.id}">

            <label>Loại</label>
            <select name="type">
                <option value="percent" ${coupon.type == 'percent' ? 'selected' : ''}>%</option>
                <option value="fixed" ${coupon.type == 'fixed' ? 'selected' : ''}>Fixed</option>
            </select>

            <label>Giá trị</label>
            <input type="number" name="value" value="${coupon.value}">

            <label>Max Discount</label>
            <input type="number" name="maxDiscount" value="${coupon.maxDiscount}">

            <label>Min Cost</label>
            <input type="number" name="minCost" value="${coupon.minCost}">

            <label>Ngày bắt đầu</label>
            <input type="date" name="startedDate"
                   value="<fmt:formatDate value='${coupon.startedDate}' pattern='yyyy-MM-dd'/>">

            <label>Ngày kết thúc</label>
            <input type="date" name="expiredDate"
                   value="<fmt:formatDate value='${coupon.expiredDate}' pattern='yyyy-MM-dd'/>">

            <label>Trạng thái</label>
            <select name="active">
                <option value="true" ${coupon.active ? 'selected' : ''}>Active</option>
                <option value="false" ${!coupon.active ? 'selected' : ''}>Inactive</option>
            </select>

            <button class="btn">Cập nhật</button>

        </form>
    </div>
</div>

</body>
</html>