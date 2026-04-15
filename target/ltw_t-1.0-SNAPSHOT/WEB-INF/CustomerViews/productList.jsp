<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ E-Fashion</title>
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; margin: 0; padding: 0; }

        /* --- CSS THANH ĐIỀU HƯỚNG BÊN TRÊN --- */
        .top-nav { display: flex; align-items: center; padding: 15px 30px; background-color: #fff; border-bottom: 1px solid #ddd; box-shadow: 0 2px 4px rgba(0,0,0,0.05); margin-bottom: 20px; gap: 25px; position: sticky; top: 0; z-index: 100;}
        .logo-text { font-size: 24px; font-weight: bold; color: #ee4d2d; text-decoration: none; margin-right: 15px; }
        .search-form { display: flex; margin-right: auto; }
        .search-input { padding: 8px 15px; border: 1px solid #ccc; border-right: none; border-radius: 4px 0 0 4px; outline: none; width: 400px; font-size: 14px; }
        .search-input:focus { border-color: #ee4d2d; }
        .search-btn { padding: 8px 20px; background-color: #ee4d2d; border: 1px solid #ee4d2d; color: white; cursor: pointer; border-radius: 0 4px 4px 0; font-size: 14px; transition: 0.2s; }
        .search-btn:hover { background-color: #d73211; }
        .nav-icon { text-decoration: none; color: #333; display: flex; align-items: center; gap: 5px; font-weight: bold; font-size: 14px; transition: color 0.2s; }
        .nav-icon:hover { color: #ee4d2d; }

        /* --- BỐ CỤC 2 CỘT: SIDEBAR VÀ CONTENT --- */
        .main-layout {
            display: flex;
            max-width: 1250px;
            margin: 0 auto;
            padding: 0 20px;
            align-items: flex-start;
        }

        /* --- CỘT TRÁI: DANH MỤC --- */
        .sidebar {
            width: 250px;
            background-color: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            padding: 15px 0;
            margin-right: 20px;
            flex-shrink: 0;
        }
        .sidebar-title {
            font-size: 16px;
            font-weight: bold;
            color: #333;
            padding: 0 20px 15px 20px;
            margin: 0;
            border-bottom: 1px solid #eee;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .category-list { list-style: none; padding: 0; margin: 0; }
        .category-item { display: block; padding: 12px 20px; color: #333; text-decoration: none; font-size: 14px; transition: 0.2s; border-left: 3px solid transparent;}
        .category-item:hover { color: #ee4d2d; background-color: #fafafa; }
        .category-item.active {
            color: #ee4d2d;
            font-weight: bold;
            background-color: #fff5f5;
            border-left: 3px solid #ee4d2d;
        }

        /* --- CỘT PHẢI: LƯỚI SẢN PHẨM --- */
        .content-area { flex-grow: 1; }
        
        /* ĐỔI MÀU NỀN THANH TIÊU ĐỀ THÀNH MÀU CAM */
        .section-header { 
            margin: 0 0 20px 0; 
            font-size: 18px; 
            color: #fff; 
            text-transform: uppercase; 
            font-weight: bold; 
            background: #ee4d2d;
            padding: 15px; 
            border-radius: 4px; 
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }

        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(190px, 1fr)); gap: 15px; }

        .product-card { background-color: #fff; border-radius: 4px; border: 1px solid #e0e0e0; overflow: hidden; text-decoration: none; transition: transform 0.2s, box-shadow 0.2s; display: flex; flex-direction: column; }
        .product-card:hover { transform: translateY(-3px); box-shadow: 0 4px 10px rgba(0,0,0,0.15); border-color: #ee4d2d; }
        .product-image { width: 100%; aspect-ratio: 1 / 1; object-fit: cover; }
        
        /* CSS CHO PHẦN THÔNG TIN SẢN PHẨM BÊN DƯỚI ẢNH */
        .product-info { padding: 10px; display: flex; flex-direction: column; flex-grow: 1; }
        .product-name { font-size: 14px; color: #222; margin: 0 0 10px 0; line-height: 1.4; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
        .product-bottom-row { display: flex; justify-content: space-between; align-items: center; margin-top: auto; }
        .product-price { font-size: 16px; color: #ee4d2d; font-weight: bold; }
        .product-sold { font-size: 12px; color: #757575; }

        /* CSS CHO KHU VỰC TOP TÌM KIẾM */
        .top-search-container {
            background-color: #fff; 
            padding: 20px;
            border-radius: 4px; 
            margin-bottom: 30px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        .top-search-header {
            font-size: 18px; color: #ee4d2d; font-weight: bold; margin-bottom: 15px; text-transform: uppercase;
            display: flex; align-items: center; gap: 8px;
        }
        .horizontal-product-list {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 15px;
        }

        /* CSS CHO KHU VỰC FLASH SALE */
        .flash-sale-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 4px;
            margin-bottom: 30px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        .flash-sale-header {
            font-size: 22px; color: #ee4d2d; font-weight: bold; margin-bottom: 15px; font-style: italic;
            display: flex; align-items: center; gap: 8px;
        }
        /* Tag giảm % dưới tên sản phẩm */
        .discount-tag {
            background: #ffeee8;
            color: #ee4d2d;
            border: 1px solid #ee4d2d;
            padding: 2px 6px;
            font-size: 11px;
            font-weight: bold;
            border-radius: 3px;
            display: inline-block;
            margin-bottom: 8px;
            width: fit-content;
        }
    </style>
</head>
<body>

    <div class="top-nav">
        <a href="${pageContext.request.contextPath}/CustomerHome" class="logo-text">E-FASHION</a>

        <form action="${pageContext.request.contextPath}/search" method="GET" class="search-form">
            <input type="text" name="keyword" class="search-input" placeholder="Tìm kiếm sản phẩm..." required value="${param.keyword}">
            <button type="submit" class="search-btn">Tìm kiếm</button>
        </form>
        
        <a href="${pageContext.request.contextPath}/my-vouchers" class="nav-icon" style="text-decoration: none; color: #333; display: flex; align-items: center; gap: 5px; font-weight: bold; font-size: 14px; margin-right: 15px;">
            <span style="font-size: 18px;">🎟️</span> Kho Voucher
        </a>
        <a href="${pageContext.request.contextPath}/order-history" class="nav-icon">
            <span style="font-size: 18px;">📦</span> Đơn Hàng
        </a>

        <a href="${pageContext.request.contextPath}/cart" class="nav-icon">
            <span style="font-size: 18px;">🛒</span> Giỏ Hàng
        </a>

        <a href="${pageContext.request.contextPath}/CustomerProfile" class="nav-icon">
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

    <div class="main-layout">
        
        <aside class="sidebar">
            <h3 class="sidebar-title">
                <span style="font-size: 18px;"></span> DANH MỤC
            </h3>
            <ul class="category-list">
                <li>
                    <a href="${pageContext.request.contextPath}/CustomerHome" 
                       class="category-item ${empty selectedCategoryId ? 'active' : ''}">
                       Tất cả sản phẩm / Gợi ý
                    </a>
                </li>
                
                <c:forEach var="cat" items="${categories}">
                    <li>
                        <a href="${pageContext.request.contextPath}/CustomerHome?categoryId=${cat.id}" 
                           class="category-item ${selectedCategoryId == cat.id ? 'active' : ''}">
                           ${cat.name}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </aside>

        <main class="content-area">
            
            <c:if test="${not empty topSearched}">
                <div class="top-search-container">
                    <div class="top-search-header">
                        <span style="font-size: 22px;"></span> TÌM KIẾM HÀNG ĐẦU
                    </div>
                    
                    <div class="horizontal-product-list">
                        <c:forEach var="sp" items="${topSearched}">
                            <a href="productdetail?id=${sp.id}" class="product-card">
                                <div class="product-image">
                                    <img src="${not empty sp.display_image ? sp.display_image : 'https://via.placeholder.com/190'}" alt="${sp.product_name}"/>
                                </div>
                                <div class="product-info">
                                    <h3 class="product-name">${sp.product_name}</h3>
                                    
                                    <div class="product-bottom-row">
                                        <div class="product-price">
                                            <fmt:formatNumber value="${sp.display_price}" pattern="#,###"/>đ
                                        </div>
                                        <div class="product-sold">
                                            Đã bán ${sp.sold_count != null ? sp.sold_count : 0}
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty flashSaleProducts}">
                <div class="flash-sale-container">
                    <div class="flash-sale-header">
                        <span style="font-size: 24px;">⚡</span> FLASH SALE
                    </div>
                    
                    <div class="horizontal-product-list">
                        <c:forEach var="sp" items="${flashSaleProducts}">
                            <a href="productdetail?id=${sp.id}" class="product-card">
                                <div class="product-image">
                                    <img src="${not empty sp.display_image ? sp.display_image : 'https://via.placeholder.com/190'}" alt="${sp.product_name}"/>
                                </div>
                                <div class="product-info">
                                    <h3 class="product-name">${sp.product_name}</h3>
                                    
                                    <c:if test="${sp.max_discount > 0}">
                                        <div class="discount-tag">
                                            Áp mã giảm thêm ${sp.max_discount}%
                                        </div>
                                    </c:if>
                                    
                                    <div class="product-bottom-row">
                                        <div class="product-price">
                                            <fmt:formatNumber value="${sp.display_price}" pattern="#,###"/>đ
                                        </div>
                                        <div class="product-sold">
                                            Đã bán ${sp.sold_count != null ? sp.sold_count : 0}
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <div class="section-header">
                ${sectionTitle}
            </div>

            <div class="product-grid">
                <c:choose>
                    <c:when test="${empty danhSachSP}">
                        <div style="grid-column: 1 / -1; text-align: center; padding: 50px; color: #777;">
                            Không tìm thấy sản phẩm nào!
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="sp" items="${danhSachSP}">
                            <a href="productdetail?id=${sp.id}" class="product-card">
                                <div class="product-image">
                                    <img src="${not empty sp.display_image ? sp.display_image : 'https://via.placeholder.com/190'}" alt="${sp.product_name}"/>
                                </div>
                                <div class="product-info">
                                    <h3 class="product-name">${sp.product_name}</h3>
                                    
                                    <div class="product-bottom-row">
                                        <div class="product-price">
                                            <fmt:formatNumber value="${sp.display_price}" pattern="#,###"/>đ
                                        </div>
                                        <div class="product-sold">
                                            Đã bán ${sp.sold_count != null ? sp.sold_count : 0}
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        
    </div>

</body>
</html>