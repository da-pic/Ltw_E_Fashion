<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <style>
        /* Reset & Cài đặt chung */
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; color: #333; margin: 0; padding: 0; }
        .product-container { display: flex; max-width: 1200px; margin: 20px auto; background: #fff; padding: 20px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
        
        /* Cột Trái: Hình ảnh */
        .product-left { width: 40%; padding-right: 30px; }
        .main-image { width: 100%; height: 450px; object-fit: cover; border: 1px solid #eee; margin-bottom: 10px; }
        .thumbnail-list { display: flex; gap: 10px; }
        .thumbnail { width: 80px; height: 80px; object-fit: cover; border: 2px solid transparent; cursor: pointer; }
        .thumbnail:hover, .thumbnail.active { border-color: #ee4d2d; }

        /* Cột Phải: Thông tin chi tiết */
        .product-right { width: 60%; }
        .product-title { font-size: 20px; font-weight: 500; margin-bottom: 15px; line-height: 1.4; }
        .badge-fav { background-color: #ee4d2d; color: white; padding: 2px 5px; font-size: 12px; border-radius: 2px; margin-right: 5px; }
        
        /* Khu vực Giá */
        .price-box { background-color: #fafafa; padding: 15px 20px; margin-bottom: 20px; display: flex; align-items: baseline; gap: 15px; }
        .price-current { color: #ee4d2d; font-size: 30px; font-weight: bold; }
        
        /* Các tùy chọn (Màu, Size) */
        .option-row { display: flex; align-items: center; margin-bottom: 20px; }
        .option-label { width: 110px; color: #757575; font-size: 14px; }
        .option-values { display: flex; gap: 10px; flex-wrap: wrap; flex: 1; }
        .btn-option { padding: 8px 15px; border: 1px solid #ccc; background: #fff; cursor: pointer; border-radius: 2px; }
        .btn-option:hover { border-color: #ee4d2d; color: #ee4d2d; }
        .btn-option.selected { border-color: #ee4d2d; color: #ee4d2d; position: relative; }
        .btn-option.selected::after { content: "✓"; position: absolute; bottom: 0; right: 0; background: #ee4d2d; color: white; font-size: 10px; padding: 1px 3px; line-height: 1; }
        .btn-option:disabled { background: #f5f5f5; color: #ccc; cursor: not-allowed; border-color: #eee; }

        /* Số lượng */
        .qty-box { display: flex; align-items: center; }
        .qty-btn { width: 32px; height: 32px; border: 1px solid #ccc; background: #fff; cursor: pointer; font-size: 16px; }
        .qty-input { width: 50px; height: 32px; border: 1px solid #ccc; border-left: none; border-right: none; text-align: center; outline: none; }
        .stock-info { margin-left: 15px; color: #757575; font-size: 14px; }

        /* Nút hành động */
        .action-buttons { display: flex; gap: 15px; margin-top: 30px; }
        .btn-cart { background: rgba(255, 87, 34, 0.1); border: 1px solid #ee4d2d; color: #ee4d2d; padding: 12px 20px; font-size: 16px; cursor: pointer; border-radius: 2px; display: flex; align-items: center; gap: 10px;}
        .btn-buy { background: #ee4d2d; border: 1px solid #ee4d2d; color: #fff; padding: 12px 30px; font-size: 16px; cursor: pointer; border-radius: 2px; }
        .btn-cart:hover { background: rgba(255, 87, 34, 0.2); }
        .btn-buy:hover { background: #d73211; }
    </style>
</head>
<body>

    <div class="product-container">
        <div class="product-left">
            <img src="https://via.placeholder.com/450" class="main-image" alt="Product Image">
            <div class="thumbnail-list">
                <img src="https://via.placeholder.com/80" class="thumbnail active">
                <img src="https://via.placeholder.com/80" class="thumbnail">
                <img src="https://via.placeholder.com/80" class="thumbnail">
            </div>
        </div>

        <div class="product-right">
            <h1 class="product-title">
                <span class="badge-fav">Yêu thích</span> ${product.product_name}
            </h1>

            <div class="price-box">
                <div class="price-current" id="displayPrice">Đang cập nhật...</div>
            </div>

            <div class="option-row">
                <div class="option-label">Màu Sắc</div>
                <div class="option-values" id="colorOptions">
                    </div>
            </div>

            <div class="option-row">
                <div class="option-label">Size</div>
                <div class="option-values" id="sizeOptions">
                     </div>
            </div>

            <div class="option-row">
                <div class="option-label">Số Lượng</div>
                <div class="qty-box">
                    <button class="qty-btn" onclick="changeQty(-1)">-</button>
                    <input type="text" class="qty-input" id="qtyInput" value="1" onchange="validateQty()">
                    <button class="qty-btn" onclick="changeQty(1)">+</button>
                </div>
                <div class="stock-info"><span id="displayStock">0</span> sản phẩm có sẵn</div>
            </div>

            <div class="action-buttons">
                <button class="btn-cart" onclick="addToCart()">
                    <i>🛒</i> Thêm Vào Giỏ Hàng
                </button>
                <button class="btn-buy" onclick="buyNow()">Mua Ngay</button>
            </div>
        </div>
    </div>

    <script>
        // 1. Chuyển đổi dữ liệu từ Backend (JSTL) sang mảng JavaScript
        const variants = [
            <c:forEach var="v" items="${product.productVariants}" varStatus="status">
                {
                    id: "${v.id}", // Cần có ID phân loại để gửi lên Server khi thêm vào giỏ
                    color: "${v.color}",
                    size: "${v.size}",
                    price: ${v.price},
                    stock: ${v.stock}
                }${!status.last ? ',' : ''}
            </c:forEach>
        ];

        let selectedColor = null;
        let selectedSize = null;
        let selectedVariantId = null;

        const uniqueColors = [...new Set(variants.map(v => v.color))];
        const uniqueSizes = [...new Set(variants.map(v => v.size))];

        function renderOptions() {
            const colorContainer = document.getElementById('colorOptions');
            const sizeContainer = document.getElementById('sizeOptions');

            colorContainer.innerHTML = uniqueColors.map(color => {
                let activeClass = (selectedColor === color) ? 'selected' : '';
                return '<button class="btn-option ' + activeClass + '" onclick="selectColor(\'' + color + '\')">' + color + '</button>';
            }).join('');

            sizeContainer.innerHTML = uniqueSizes.map(size => {
                let activeClass = (selectedSize === size) ? 'selected' : '';
                return '<button class="btn-option ' + activeClass + '" onclick="selectSize(\'' + size + '\')">' + size + '</button>';
            }).join('');
        }

        function selectColor(color) {
            selectedColor = (selectedColor === color) ? null : color;
            updateUI();
        }

        function selectSize(size) {
            selectedSize = (selectedSize === size) ? null : size;
            updateUI();
        }

        function updateUI() {
            renderOptions(); 
            
            const displayPrice = document.getElementById('displayPrice');
            const displayStock = document.getElementById('displayStock');
            const qtyInput = document.getElementById('qtyInput');

            if (selectedColor && selectedSize) {
                const matchedVariant = variants.find(v => v.color === selectedColor && v.size === selectedSize);
                
                if (matchedVariant) {
                    selectedVariantId = matchedVariant.id;
                    displayPrice.innerText = new Intl.NumberFormat('vi-VN').format(matchedVariant.price) + 'đ';
                    displayStock.innerText = matchedVariant.stock;
                    
                    if (parseInt(qtyInput.value) > matchedVariant.stock) {
                        qtyInput.value = matchedVariant.stock;
                    }
                } else {
                    selectedVariantId = null;
                    displayPrice.innerText = "Hết hàng / Không có phân loại này";
                    displayStock.innerText = "0";
                }
            } else {
                selectedVariantId = null;
                displayPrice.innerText = "Vui lòng chọn phân loại";
                
                let totalStock = variants.reduce((total, v) => total + v.stock, 0);
                displayStock.innerText = totalStock;
            }
        }

        function changeQty(amount) {
            const input = document.getElementById('qtyInput');
            let currentVal = parseInt(input.value) || 1;
            let maxStock = parseInt(document.getElementById('displayStock').innerText);
            
            let newVal = currentVal + amount;
            if (newVal >= 1 && newVal <= maxStock) {
                input.value = newVal;
            }
        }

        function validateQty() {
            const input = document.getElementById('qtyInput');
            let val = parseInt(input.value) || 1;
            let maxStock = parseInt(document.getElementById('displayStock').innerText);
            
            if (val < 1) input.value = 1;
            if (val > maxStock) input.value = maxStock;
        }

        function addToCart() {
            if (!selectedColor || !selectedSize || !selectedVariantId) {
                alert("Vui lòng chọn đầy đủ Màu sắc và Size trước khi thêm vào giỏ hàng!");
                return;
            }
            const quantity = document.getElementById('qtyInput').value;
            
            window.location.href = "${pageContext.request.contextPath}/cart?action=add&variantId=" + selectedVariantId + "&quantity=" + quantity;
        }

        function buyNow() {
            if (!selectedColor || !selectedSize || !selectedVariantId) {
                alert("Vui lòng chọn đầy đủ Màu sắc và Size trước khi mua!");
                return;
            }
            const quantity = document.getElementById('qtyInput').value;
            
            window.location.href = "${pageContext.request.contextPath}/checkout?variantId=" + selectedVariantId + "&quantity=" + quantity;
        }

        renderOptions();
        updateUI();
    </script>
</body>
</html>