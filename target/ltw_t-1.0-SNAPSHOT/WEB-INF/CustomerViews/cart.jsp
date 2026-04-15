<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng Của Bạn</title>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; margin: 0; padding: 0; }

        /* --- CSS THANH ĐIỀU HƯỚNG --- */
        .top-nav { display: flex; align-items: center; padding: 15px 30px; background-color: #fff; border-bottom: 1px solid #ddd; box-shadow: 0 2px 4px rgba(0,0,0,0.05); margin-bottom: 20px; gap: 25px; }
        .search-form { display: flex; margin-right: auto; }
        .search-input { padding: 8px 15px; border: 1px solid #ccc; border-right: none; border-radius: 4px 0 0 4px; outline: none; width: 350px; font-size: 14px; }
        .search-input:focus { border-color: #ee4d2d; }
        .search-btn { padding: 8px 20px; background-color: #ee4d2d; border: 1px solid #ee4d2d; color: white; cursor: pointer; border-radius: 0 4px 4px 0; font-size: 14px; transition: 0.2s; }
        .search-btn:hover { background-color: #d73211; }
        .cart-icon, .user-profile { text-decoration: none; color: #333; display: flex; align-items: center; gap: 5px; font-weight: bold; font-size: 14px; transition: color 0.2s; }
        .cart-icon:hover, .user-profile:hover { color: #ee4d2d; }

        /* --- CSS RIÊNG CHO TRANG GIỎ HÀNG --- */
        .cart-container { max-width: 1200px; margin: 0 auto 40px auto; background: #fff; padding: 20px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
        .cart-header { font-size: 20px; color: #ee4d2d; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 20px; text-transform: uppercase; text-align: center;}
        
        .cart-table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
        .cart-table th { background-color: #fafafa; padding: 15px 10px; text-align: center; border-bottom: 1px solid #ddd; color: #757575; font-size: 14px; }
        .cart-table td { padding: 15px 10px; text-align: center; border-bottom: 1px solid #eee; vertical-align: middle; }
        
        /* Checkbox Style */
        .custom-checkbox { width: 18px; height: 18px; cursor: pointer; accent-color: #ee4d2d; }

        .item-info { display: flex; align-items: center; text-align: left; gap: 15px; }
        .item-image { width: 80px; height: 80px; object-fit: cover; border: 1px solid #eee; }
        .item-variant { font-size: 13px; color: #757575; margin-top: 5px; }
        
        .price-text { color: #333; }
        .total-text { color: #ee4d2d; font-weight: bold; }
        
        /* Quantity Buttons */
        .qty-control { display: inline-flex; align-items: center; border: 1px solid #ddd; border-radius: 2px; overflow: hidden; }
        .qty-btn { text-decoration: none; color: #333; background-color: #fafafa; padding: 5px 12px; font-weight: bold; transition: 0.2s; }
        .qty-btn:hover { background-color: #eee; }
        .qty-number { padding: 5px 15px; border-left: 1px solid #ddd; border-right: 1px solid #ddd; min-width: 20px; text-align: center; }

        .btn-remove { background: none; border: none; color: #757575; cursor: pointer; text-decoration: underline; font-size: 14px; transition: 0.2s; }
        .btn-remove:hover { color: #ee4d2d; }
        
        .cart-footer { display: flex; justify-content: space-between; align-items: center; border-top: 1px dashed #ddd; padding-top: 20px; }
        .continue-shopping { text-decoration: none; color: #555; border: 1px solid #ccc; padding: 10px 20px; border-radius: 2px; transition: 0.2s; }
        .continue-shopping:hover { background-color: #f5f5f5; }
        
        .summary-box { display: flex; align-items: center; gap: 30px; }
        .grand-total { font-size: 18px; }
        .grand-total span { font-size: 24px; color: #ee4d2d; font-weight: bold; margin-left: 10px; }
        
        .btn-checkout { background-color: #ee4d2d; color: white; border: none; padding: 12px 30px; font-size: 16px; border-radius: 2px; cursor: pointer; transition: 0.2s; text-decoration: none; }
        .btn-checkout:hover { background-color: #d73211; }
        
        .empty-cart { text-align: center; padding: 50px 0; color: #757575; }
    </style>
</head>
<body>

    <div class="top-nav">
        <form action="${pageContext.request.contextPath}/search" method="GET" class="search-form">
            <input type="text" name="keyword" class="search-input" placeholder="Tìm kiếm sản phẩm..." required>
            <button type="submit" class="search-btn">Tìm kiếm</button>
        </form>

        <a href="${pageContext.request.contextPath}/cart" class="cart-icon" style="color: #ee4d2d;"> 
            <span style="font-size: 18px;">🛒</span> Giỏ Hàng
        </a>

        <a href="${pageContext.request.contextPath}/CustomerProfile" class="user-profile">
            <span class="avatar" style="font-size: 18px;">👤</span>
            <span class="username">
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser}">
                        ${sessionScope.currentUser.name} 
                    </c:when>
                    <c:otherwise>
                        Tài Khoản
                    </c:otherwise>
                </c:choose>
            </span>
        </a>
    </div>

    <div class="cart-container">
        <h1 class="cart-header">Giỏ Hàng Của Bạn</h1>

        <c:choose>
            <c:when test="${empty cart or empty cart.items}">
                <div class="empty-cart">
                    <div style="font-size: 50px; margin-bottom: 20px;">🛒</div>
                    <h3>Giỏ hàng của bạn còn trống</h3>
                    <p>Mua sắm ngay để nhận nhiều ưu đãi!</p><br>
                    <a href="${pageContext.request.contextPath}/CustomerHome" class="continue-shopping" style="display: inline-block;">Tiếp tục mua sắm</a>
                </div>
            </c:when>
            
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/checkout" method="GET" id="checkoutForm">
                    <input type="hidden" name="cartId" value="${cart.id}">

                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th style="width: 40px;"><input type="checkbox" id="selectAll" class="custom-checkbox" checked></th>
                                <th style="text-align: left;">Sản Phẩm</th>
                                <th>Đơn Giá</th>
                                <th>Số Lượng</th>
                                <th>Số Tiền</th>
                                <th>Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cart.items}">
                                <tr>
                                    <td>
                                        <input type="checkbox" name="selectedVariants" value="${item.product_variant_id}" class="custom-checkbox item-checkbox" data-price="${item.totalPrice}" checked>
                                    </td>

                                    <td>
                                        <div class="item-info">
                                            <img src="${item.productVariant.image}" class="item-image" alt="Product">
                                            <div>
                                                <div style="font-weight: bold; color: #333; font-size: 15px; margin-bottom: 4px;">
                                                    ${item.productName != null ? item.productName : "Sản phẩm E-Fashion"}
                                                </div>
                                                <div class="item-variant">Mã Phân Loại: ${item.product_variant_id}</div>
                                                <div class="item-variant">Kích cỡ: ${item.productVariant.size} | Màu sắc: ${item.productVariant.color}</div>
                                            </div>
                                        </div>
                                    </td>
                                    
                                    <td class="price-text">${item.unit_price}đ</td>
                                    
                                    <td>
                                        <div class="qty-control">
                                            <a href="${pageContext.request.contextPath}/cart?action=decrease&variantId=${item.product_variant_id}" class="qty-btn">-</a>
                                            <span class="qty-number">${item.amount}</span>
                                            <a href="${pageContext.request.contextPath}/cart?action=add&variantId=${item.product_variant_id}&quantity=1" class="qty-btn">+</a>
                                        </div>
                                    </td>
                                    
                                    <td class="total-text">${item.totalPrice}đ</td>
                                    
                                    <td>
                                        <a href="${pageContext.request.contextPath}/cart?action=remove&variantId=${item.product_variant_id}" class="btn-remove" onclick="return confirm('Bỏ sản phẩm này?');">Xóa</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="cart-footer">
                        <a href="${pageContext.request.contextPath}/CustomerHome" class="continue-shopping">← Tiếp tục mua sắm</a>
                        
                        <div class="summary-box">
                            <div class="grand-total">Tổng thanh toán: <span id="displayGrandTotal">${cart.cartTotalAmount}đ</span></div>
                            
                            <button type="submit" class="btn-checkout" id="btnBuy" onclick="return validateCheckout()">Mua Hàng</button>
                        </div>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const selectAllCb = document.getElementById('selectAll');
            const itemCbs = document.querySelectorAll('.item-checkbox');
            const displayTotal = document.getElementById('displayGrandTotal');

            function calculateTotal() {
                let total = 0;
                itemCbs.forEach(cb => {
                    if (cb.checked) {
                        total += parseInt(cb.getAttribute('data-price'));
                    }
                });
                displayTotal.innerText = total + "đ";
            }

            if(selectAllCb && itemCbs) {
                selectAllCb.addEventListener('change', function() {
                    itemCbs.forEach(cb => cb.checked = selectAllCb.checked);
                    calculateTotal();
                });

                itemCbs.forEach(cb => {
                    cb.addEventListener('change', function() {
                        if (!this.checked) selectAllCb.checked = false;
                        
                        let allChecked = true;
                        itemCbs.forEach(i => { if(!i.checked) allChecked = false; });
                        selectAllCb.checked = allChecked;

                        calculateTotal();
                    });
                });
            }
        });

        function validateCheckout() {
            const itemCbs = document.querySelectorAll('.item-checkbox');
            let hasChecked = false;
            itemCbs.forEach(cb => { if(cb.checked) hasChecked = true; });
            
            if(!hasChecked) {
                alert("Vui lòng chọn ít nhất 1 sản phẩm để thanh toán!");
                return false; 
            }
            return true; 
        }
    </script>
</body>
</html>