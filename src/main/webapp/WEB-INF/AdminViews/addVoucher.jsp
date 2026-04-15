<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Voucher</title>

    <style>
        body { font-family: Arial; background: #f4f6f9; padding: 30px; }
        .form-box {
            background: white;
            padding: 25px;
            width: 500px;
            margin: auto;
            border-radius: 8px;
        }
        input, select {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
        }
        button {
            background: #e64a19;
            color: white;
            padding: 10px;
            border: none;
            width: 100%;
        }
    </style>
</head>
<body>

<div class="form-box">
    <h2>Thêm Voucher</h2>

    <form method="post" action="${pageContext.request.contextPath}/admin/vouchers">

        <input type="hidden" name="action" value="add"/>

        <label>ID</label>
        <input type="text" name="id" required/>

        <label>Loại</label>
        <select name="type">
            <option value="ship">shipment</option>
            <option value="product">product</option>
        </select>

        <label>Giá trị</label>
        <input type="number" name="value" required/>

        <label>Max Discount</label>
        <input type="number" name="maxDiscount"/>

        <label>Min Cost</label>
        <input type="number" name="minCost"/>

        <label>Ngày bắt đầu</label>
        <input type="date" name="startedDate" required/>

        <label>Ngày kết thúc</label>
        <input type="date" name="expiredDate" required/>

        <label>Trạng thái</label>
        <select name="active">
            <option value="true">Active</option>
            <option value="false">Inactive</option>
        </select>

        <button type="submit">Thêm Voucher</button>

    </form>
</div>

</body>
</html>