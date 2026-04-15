<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán Đơn Hàng</title>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; margin: 0; padding: 0; }
        
        /* CSS Thanh điều hướng đơn giản cho trang Checkout */
        .top-nav { padding: 15px 30px; background-color: #fff; border-bottom: 1px solid #ee4d2d; margin-bottom: 20px; display: flex; align-items: center; }
        .logo-text { font-size: 24px; font-weight: bold; color: #ee4d2d; text-decoration: none; margin-right: 15px; }
        .nav-title { font-size: 20px; color: #ee4d2d; border-left: 1px solid #ee4d2d; padding-left: 15px; }

        .checkout-container { max-width: 1200px; margin: 0 auto 40px auto; display: flex; gap: 20px; padding: 0 20px;}
        
        .section-box { background: #fff; padding: 25px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .section-title { font-size: 18px; color: #ee4d2d; margin-bottom: 15px; text-transform: uppercase; }
        
        /* Cột trái (Thông tin địa chỉ) */
        .left-col { flex: 2; }
        .address-card { border: 1px dashed #ccc; padding: 15px; margin-bottom: 10px; border-radius: 4px; cursor: pointer; display: flex; align-items: flex-start; transition: 0.2s;}
        .address-card:hover { border-color: #ee4d2d; background-color: #fffafa; }
        .address-radio { margin-right: 15px; accent-color: #ee4d2d; margin-top: 5px;}
        .new-address-link { color: #0056b3; text-decoration: none; font-size: 14px; font-weight: bold;}
        
        /* Cột phải (Sản phẩm & Tổng tiền) */
        .right-col { flex: 1; }
        .product-item { display: flex; gap: 15px; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 15px; }
        .product-item img { width: 60px; height: 60px; object-fit: cover; border: 1px solid #ddd; }
        .product-details h4 { font-size: 14px; margin: 0 0 5px 0; color: #333; }
        .product-details p { font-size: 12px; color: #757575; margin: 0 0 5px 0; }
        .product-price { font-weight: bold; color: #ee4d2d; font-size: 14px;}
        
        .summary-row { display: flex; justify-content: space-between; margin-bottom: 12px; font-size: 14px; color: #555;}
        .summary-total { font-size: 16px; color: #333; margin-top: 15px; border-top: 1px solid #eee; padding-top: 15px; align-items: center;}
        .summary-total span:last-child { font-size: 24px; font-weight: bold; color: #ee4d2d; }
        
        .btn-place-order { width: 100%; background: #ee4d2d; color: #fff; border: none; padding: 15px; font-size: 16px; font-weight: bold; border-radius: 2px; cursor: pointer; transition: 0.2s; margin-top: 20px; }
        .btn-place-order:hover { background: #d73211; }
    </style>
</head>
<body>

    <div class="top-nav">
        <a href="${pageContext.request.contextPath}/CustomerHome" class="logo-text">E-FASHION</a>
        <span class="nav-title">Thanh Toán</span>
    </div>

    <div class="checkout-container">
        
        <div class="left-col">
            <form action="${pageContext.request.contextPath}/checkout" method="POST" id="finalCheckoutForm">
                <input type="hidden" name="cartId" value="${cartId}">
                <c:forEach var="item" items="${checkoutItems}">
                    <input type="hidden" name="variantIds" value="${item.productVariantId}">
                    <input type="hidden" name="quantities" value="${item.amount}">
                </c:forEach>

                <div class="section-box">
                    <h2 class="section-title">📍 Địa Chỉ Nhận Hàng</h2>
                    
                    <c:choose>
                        <c:when test="${not empty addresses}">
                            <c:forEach var="addr" items="${addresses}" varStatus="status">
                                <label class="address-card">
                                    <input type="radio" name="addressId" value="${addr.id}" class="address-radio" ${status.first ? 'checked' : ''} required>
                                    <div>
                                        <div style="font-weight: bold; margin-bottom: 5px; color: #333;">SĐT: ${addr.phone_number}</div>
                                        <div style="color: #555; font-size: 14px; line-height: 1.4;">
                                            ${addr.detail}<br>
                                            ${addr.street}, ${addr.ward}, ${addr.district}, ${addr.city}
                                        </div>
                                    </div>
                                </label>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="color: #ee4d2d; font-style: italic;">Bạn chưa thiết lập địa chỉ giao hàng.</p>
                        </c:otherwise>
                    </c:choose>
                    
                    <div style="margin-top: 15px;">
                        <a href="#" class="new-address-link">+ Thêm địa chỉ mới</a>
                    </div>
                </div>

                <div class="section-box">
                    <h2 class="section-title">💳 Phương Thức Thanh Toán</h2>
                    <label style="display: flex; align-items: center; margin-bottom: 15px; cursor: pointer;">
                        <input type="radio" name="paymentMethod" value="cash" checked class="address-radio"> 
                        <span>Thanh toán khi nhận hàng (COD)</span>
                    </label>
                    <label style="display: flex; align-items: center; cursor: pointer;">
                        <input type="radio" name="paymentMethod" value="transfer" class="address-radio"> 
                        <span>Chuyển khoản ngân hàng</span>
                    </label>
                </div>

                <button type="submit" class="btn-place-order">ĐẶT HÀNG</button>
            </form>
        </div>

        <div class="right-col">
            <div class="section-box">
                <h2 class="section-title">Đơn Hàng (${checkoutItems.size()} Sản phẩm)</h2>
                
                <div style="max-height: 400px; overflow-y: auto; padding-right: 5px;">
                    <c:forEach var="item" items="${checkoutItems}">
                        <div class="product-item">
                            <img src="${item.productVariant.image}" alt="Product">
                            <div class="product-details" style="flex: 1;">
                                <h4>Mã loại: ${item.productVariantId}</h4>
                                <p>Màu: ${item.productVariant.color} | Size: ${item.productVariant.size}</p>
                                <div style="display: flex; justify-content: space-between; align-items: center;">
                                    <div class="product-price">${item.unitPrice}đ</div>
                                    <div style="font-size: 12px; color: #555;">x${item.amount}</div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div style="margin-top: 20px; border-top: 1px dashed #eee; padding-top: 15px;">
                    <h3 style="font-size: 14px; margin-bottom: 10px; color: #333;">🎟️ Chọn Mã Giảm Giá</h3>
                    <select id="voucherSelect" onchange="applySelectedVoucher()" style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 2px; font-size: 14px; cursor: pointer;">
                        <option value="">-- Không sử dụng mã giảm giá --</option>
                        <c:forEach var="v" items="${myVouchers}">
                            <c:if test="${!v.is_expired && grandTotal >= v.min_cost}">
                                <option value="${v.id}" data-type="${v.type}" data-value="${v.value}" data-max="${v.max_discount}">
                                    Mã ${v.id}: Giảm <c:out value="${v.type == 'percent' ? v.value += '%' : v.value += 'đ'}"/> 
                                    (Đơn từ ${v.min_cost}đ)
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="summary-row" style="margin-top: 20px;">
                    <span>Tổng tiền hàng:</span>
                    <span id="baseTotal" data-value="${grandTotal}">${grandTotal}đ</span>
                </div>
                <div class="summary-row">
                    <span>Phí vận chuyển:</span>
                    <span>0đ</span>
                </div>
                <div class="summary-row" id="discountRow" style="display: none; color: #00bfa5;">
                    <span>Giảm giá (Voucher):</span>
                    <span id="discountValue">-0đ</span>
                </div>
                <div class="summary-row summary-total">
                    <span>Tổng thanh toán:</span>
                    <span id="finalTotal">${grandTotal}đ</span>
                </div>
                
                <input type="hidden" name="appliedCoupon" id="appliedCouponInput" form="finalCheckoutForm" value="">
                
            </div>
        </div>

    </div>

    <script>
        function applySelectedVoucher() {
            const select = document.getElementById('voucherSelect');
            const selectedOption = select.options[select.selectedIndex];
            const baseTotalStr = document.getElementById('baseTotal').getAttribute('data-value');
            const baseTotal = parseInt(baseTotalStr);

            if (!selectedOption.value) {
                document.getElementById('discountRow').style.display = 'none';
                document.getElementById('finalTotal').innerText = baseTotal + 'đ';
                document.getElementById('appliedCouponInput').value = '';
                return;
            }

            const type = selectedOption.getAttribute('data-type');
            const value = parseInt(selectedOption.getAttribute('data-value'));
            const maxDiscount = parseInt(selectedOption.getAttribute('data-max'));
            let discountAmount = 0;

            if (type === 'percent') {
                discountAmount = (baseTotal * value) / 100;
                if (maxDiscount > 0 && discountAmount > maxDiscount) {
                    discountAmount = maxDiscount;
                }
            } else {
                discountAmount = value; 
            }

            const newTotal = baseTotal - discountAmount;
            document.getElementById('discountRow').style.display = 'flex';
            document.getElementById('discountValue').innerText = '-' + discountAmount + 'đ';
            document.getElementById('finalTotal').innerText = newTotal + 'đ';
            document.getElementById('appliedCouponInput').value = selectedOption.value;
        }
    </script>
</body>
</html>