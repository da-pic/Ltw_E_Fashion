<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Sản Phẩm</title>
    <style>
        body {
            background-color: #f5f5f5; 
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0; /* Đổi padding thành 0 để thanh nav sát mép trên */
        }

        /* ================= THANH ĐIỀU HƯỚNG BÊN TRÊN ================= */
        .top-nav {
            display: flex;
            justify-content: flex-end; /* Đẩy nội dung sang góc phải */
            padding: 15px 30px;
            background-color: #fff;
            border-bottom: 1px solid #ddd;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }

        /* Nút user là một thẻ <a> nên cần bỏ gạch chân */
        .user-profile {
            text-decoration: none; 
            color: #333;
            display: flex;
            align-items: center;
            gap: 8px;
            font-weight: bold;
            font-size: 14px;
            transition: color 0.2s;
        }

        .user-profile:hover {
            color: #ee4d2d; /* Đổi màu khi di chuột vào */
        }

        /* ================= DANH SÁCH SẢN PHẨM ================= */
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(190px, 1fr));
            gap: 15px; 
            max-width: 1200px;
            margin: 0 auto 40px auto; 
            padding: 0 20px; /* Thêm padding để không chạm sát mép màn hình 2 bên */
        }

        .product-card {
            background-color: #fff;
            border-radius: 4px;
            border: 1px solid #e0e0e0;
            overflow: hidden;
            text-decoration: none; 
            transition: transform 0.2s, box-shadow 0.2s;
            display: flex;
            flex-direction: column;
        }

        .product-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            border-color: #ee4d2d;
        }

        .product-image {
            width: 100%;
            aspect-ratio: 1 / 1; 
            object-fit: cover;
        }

        .product-info {
            padding: 10px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }

        .product-name {
            font-size: 14px;
            color: #222;
            margin: 0 0 10px 0;
            line-height: 1.4;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .product-price {
            font-size: 16px;
            color: #ee4d2d; 
            font-weight: bold;
            margin-top: auto; 
        }
        
        .currency {
            font-size: 12px;
            text-decoration: underline;
        }
        
        .product-footer {
            font-size: 12px;
            color: #757575;
            margin-top: 5px;
            text-align: right;
        }
    </style>
</head>
<body>

    <div class="top-nav">
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

    <h2 style="text-align: center; color: #ee4d2d;">GỢI Ý HÔM NAY</h2>

    <div class="product-grid">
        
        <c:forEach var="sp" items="${danhSachSP}">
            
            <a href="productdetail?id=${sp.id}" class="product-card">
                <div class="product-info">
                    <h3 class="product-name">${sp.product_name}</h3>
                    <div class="product-image">
                        <img src="${sp.display_image}" alt="${sp.product_name}" style="width: 100%; height: 100%; object-fit: cover;"/>
                    </div>
                    <div class="product-price">
                        ${sp.display_price}
                    </div>  
                    <div class="product-footer">
                        Mã: ${sp.id}
                    </div>
                </div>
            </a>
            
        </c:forEach>
        
    </div>

</body>
</html>