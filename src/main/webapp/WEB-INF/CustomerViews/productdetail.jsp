<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; color: #333; margin: 0; padding: 0; }
        
        /* CSS CHO NÚT QUAY LẠI (BREADCRUMB) */
        .breadcrumb {
            max-width: 1200px;
            margin: 20px auto 0 auto;
            padding: 0 20px;
        }
        .btn-back {
            display: inline-block;
            text-decoration: none;
            color: #ee4d2d;
            font-size: 14px;
            font-weight: bold;
            padding: 8px 15px;
            border: 1px solid #ee4d2d;
            border-radius: 4px;
            background-color: #fff;
            transition: all 0.2s;
        }
        .btn-back:hover {
            background-color: #ee4d2d;
            color: #fff;
        }
        /* --------------------------------- */

        .product-container { display: flex; max-width: 1200px; margin: 20px auto; background: #fff; padding: 30px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
        
        .product-left { width: 40%; padding-right: 30px; }
        .main-image { width: 100%; height: 450px; object-fit: cover; border: 1px solid #eee; margin-bottom: 10px; border-radius: 2px;}

        .product-right { width: 60%; }
        .product-title { font-size: 22px; font-weight: 500; margin-bottom: 15px; line-height: 1.4; }
        .badge-fav { color: white; padding: 2px 5px; font-size: 12px; border-radius: 2px; margin-right: 5px; vertical-align: middle;}
        
        .price-box { background-color: #fafafa; padding: 15px 20px; margin-bottom: 25px; display: flex; align-items: baseline; gap: 15px; border-radius: 2px;}
        .price-current { color: #ee4d2d; font-size: 30px; font-weight: bold; }
        .price-note { color: #757575; font-size: 14px; }
        
        .option-row { display: flex; align-items: center; margin-bottom: 20px; }
        .option-label { width: 110px; color: #757575; font-size: 14px; }
        
        .variant-select { 
            flex: 1; 
            padding: 12px; 
            border: 1px solid #ccc; 
            border-radius: 2px; 
            font-size: 14px; 
            outline: none; 
            cursor: pointer;
        }
        .variant-select:focus { border-color: #ee4d2d; }
        
        .qty-input { 
            width: 70px; 
            height: 38px; 
            border: 1px solid #ccc; 
            text-align: center; 
            outline: none; 
            border-radius: 2px;
            font-size: 16px;
        }
        .qty-input:focus { border-color: #ee4d2d; }
        .stock-info { margin-left: 15px; font-size: 14px; color: #757575; }

        .action-buttons { display: flex; gap: 15px; margin-top: 30px; }
        .btn-cart { background: rgba(255, 87, 34, 0.1); border: 1px solid #ee4d2d; color: #ee4d2d; padding: 12px 20px; font-size: 16px; cursor: pointer; border-radius: 2px; display: flex; align-items: center; gap: 10px; transition: 0.2s;}
        .btn-buy { background: #ee4d2d; border: 1px solid #ee4d2d; color: #fff; padding: 12px 30px; font-size: 16px; cursor: pointer; border-radius: 2px; transition: 0.2s;}
        .btn-cart:hover { background: rgba(255, 87, 34, 0.2); }
        .btn-buy:hover { background: #d73211; }

        /* KHỐI MÔ TẢ SẢN PHẨM */
        .desc-container { max-width: 1200px; margin: 0 auto 40px auto; background: #fff; padding: 30px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
        .desc-title { font-size: 18px; text-transform: uppercase; margin-bottom: 15px; border-bottom: 1px solid #eee; padding-bottom: 10px; color: #333;}
        .desc-content { font-size: 14px; color: #555; line-height: 1.6; white-space: pre-wrap;}
    </style>
</head>
<body>

    <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/CustomerHome" class="btn-back">
            &larr; Quay lại danh sách sản phẩm
        </a>
    </div>

    <div class="product-container">
        <div class="product-left">
            <img id="mainImage" src="${not empty product.display_image ? product.display_image : 'https://via.placeholder.com/450'}" class="main-image" alt="${product.product_name}">
        </div>

        <div class="product-right">
            <h1 class="product-title">
                <span class="badge-fav"></span> ${product.product_name}
            </h1>

            <div class="price-box">
                <div class="price-current" id="displayPrice">${product.display_price}đ</div>
                <div class="price-note">(Vui lòng chọn phân loại để xem giá chính xác)</div>
            </div>

            <form id="detailForm" action="${pageContext.request.contextPath}/cart" method="GET" onsubmit="return validateForm()">
                
                <div class="option-row">
                    <div class="option-label">Phân loại</div>
                    <select name="variantId" id="variantSelect" class="variant-select" onchange="updateVariantInfo()" required>
                        <option value="" disabled selected>-- Chọn Màu Sắc & Kích Cỡ --</option>
                        <c:forEach var="v" items="${product.productVariants}">
                            <option value="${v.id}">
                                ${v.color} | Size: ${v.size}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="option-row">
                    <div class="option-label">Số Lượng</div>
                    <input type="number" name="quantity" id="qtyInput" class="qty-input" value="1" min="1" required>
                    <span class="stock-info" id="stockDisplay"></span>
                </div>

                <div class="action-buttons">
                    <button type="submit" name="action" value="add" class="btn-cart">
                        <i>🛒</i> Thêm Vào Giỏ Hàng
                    </button>
                    
                    <button type="submit" name="action" value="buy" class="btn-buy" formaction="${pageContext.request.contextPath}/checkout">
                        Mua Ngay
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="desc-container">
        <h2 class="desc-title">Mô tả sản phẩm</h2>
        <div class="desc-content">${product.description}</div>
    </div>

    <script>
        const variantsData = [
            <c:forEach var="v" items="${product.productVariants}">
                {
                    id: '${v.id}',
                    price: ${v.price},
                    stock: ${v.stock},
                    image: '${v.image}'
                },
            </c:forEach>
        ];

        function updateVariantInfo() {
            const selectBox = document.getElementById('variantSelect');
            const selectedId = selectBox.value;
            
            const variant = variantsData.find(v => v.id === selectedId);
            
            if (variant) {
                document.getElementById('displayPrice').innerText = variant.price + 'đ';
                
                if (variant.image && variant.image.trim() !== '') {
                    document.getElementById('mainImage').src = variant.image;
                }

                document.getElementById('stockDisplay').innerText = '(Còn ' + variant.stock + ' sản phẩm)';
                const qtyInput = document.getElementById('qtyInput');
                qtyInput.max = variant.stock;

                if (parseInt(qtyInput.value) > variant.stock) {
                    qtyInput.value = variant.stock;
                }
            }
        }

        function validateForm() {
            const selectBox = document.getElementById('variantSelect');
            const qtyInput = document.getElementById('qtyInput');
            
            if (!selectBox.value) {
                alert("Vui lòng chọn phân loại sản phẩm!");
                return false;
            }
            
            const variant = variantsData.find(v => v.id === selectBox.value);
            if (variant && variant.stock <= 0) {
                alert("Rất tiếc, phân loại này hiện đã hết hàng!");
                return false;
            }
            
            if (parseInt(qtyInput.value) <= 0 || parseInt(qtyInput.value) > variant.stock) {
                alert("Số lượng không hợp lệ!");
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>