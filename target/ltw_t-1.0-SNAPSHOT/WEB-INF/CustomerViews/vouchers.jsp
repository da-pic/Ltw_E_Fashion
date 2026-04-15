<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kho Voucher Của Tôi</title>
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; }
        
        .header-title { font-size: 24px; color: #ee4d2d; margin-bottom: 25px; border-bottom: 2px solid #ddd; padding-bottom: 10px; display: flex; justify-content: space-between; align-items: center;}
        .btn-back { font-size: 14px; color: #555; text-decoration: none; border: 1px solid #ccc; padding: 6px 15px; border-radius: 2px; transition: 0.2s;}
        .btn-back:hover { background-color: #ee4d2d; color: #fff; border-color: #ee4d2d; }
        
        .voucher-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        
        /* CSS VÉ RĂNG CƯA */
        .voucher-card {
            display: flex;
            background: #fff;
            height: 120px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.08);
            border: 1px solid #eee;
            position: relative;
            overflow: hidden;
            transition: 0.2s;
            border-radius: 4px;
        }
        .voucher-card:hover { transform: translateY(-3px); box-shadow: 0 4px 10px rgba(238, 77, 45, 0.2); border-color: #ee4d2d; }
        
        /* Hiệu ứng đục lỗ 2 bên */
        .voucher-card::before, .voucher-card::after {
            content: ""; position: absolute; width: 20px; height: 20px;
            background-color: #f5f5f5; border-radius: 50%;
            top: 50%; transform: translateY(-50%);
            border: 1px solid #eee;
            z-index: 1;
        }
        .voucher-card::before { left: 110px; border-left: none; }
        .voucher-card::after { left: 110px; border-right: none; margin-left: -20px;}

        /* Cột trái (Màu cam) */
        .v-left {
            width: 120px; background-color: #ee4d2d; color: white; display: flex; flex-direction: column;
            justify-content: center; align-items: center; border-right: 2px dashed #fff; padding: 10px; text-align: center;
        }
        
        /* Cột phải (Thông tin) */
        .v-right { flex: 1; padding: 15px 20px 15px 30px; display: flex; flex-direction: column; justify-content: center; position: relative;}
        .v-title { font-size: 18px; font-weight: bold; color: #333; margin: 0 0 5px 0; }
        .v-desc { font-size: 13px; color: #757575; margin: 0 0 10px 0; line-height: 1.4;}
        .v-date { font-size: 12px; color: #ee4d2d; }
        .v-code { position: absolute; top: 15px; right: 15px; background: #ffeee8; color: #ee4d2d; border: 1px dashed #ee4d2d; padding: 4px 8px; font-weight: bold; font-size: 12px; border-radius: 2px;}
        
        /* Khi vé bị hết hạn */
        .voucher-expired { filter: grayscale(100%); opacity: 0.6; pointer-events: none; }
    </style>
</head>
<body>

    <div class="container">
        <div class="header-title">
            <span>🎟️ Kho Voucher Của Tôi</span>
            <a href="${pageContext.request.contextPath}/CustomerHome" class="btn-back">← Về trang chủ</a>
        </div>

        <c:choose>
            <c:when test="${empty vouchers}">
                <div style="text-align: center; padding: 80px 0; color: #777; background: #fff; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
                    <span style="font-size: 50px;">🎫</span><br><br>
                    <div style="font-size: 16px;">Ví của bạn hiện chưa có mã giảm giá nào.</div>
                    <div style="font-size: 14px; margin-top: 10px;">Hãy mua sắm thêm để nhận nhiều ưu đãi nhé!</div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="voucher-grid">
                    <c:forEach var="v" items="${vouchers}">
                        <div class="voucher-card ${v.is_expired ? 'voucher-expired' : ''}">
                            <div class="v-left">
                                <span style="font-size: 32px;">🎁</span>
                                <span style="font-size: 11px; font-weight: bold; margin-top: 8px; text-transform: uppercase;">E-FASHION</span>
                            </div>
                            <div class="v-right">
                                <div class="v-code">${v.id}</div>
                                
                                <h3 class="v-title">
                                    <c:choose>
                                        <c:when test="${v.type == 'percent'}">Giảm ${v.value}%</c:when>
                                        <c:otherwise>Giảm ${v.value}đ</c:otherwise>
                                    </c:choose>
                                </h3>
                                
                                <p class="v-desc">
                                    Đơn tối thiểu ${v.min_cost}đ.<br>
                                    <c:if test="${v.max_discount > 0}">Giảm tối đa ${v.max_discount}đ.</c:if>
                                    (Còn ${v.usage_limit} lượt dùng)
                                </p>
                                
                                <div class="v-date">
                                    <c:choose>
                                        <c:when test="${v.is_expired}">Đã hết hạn</c:when>
                                        <c:otherwise>
                                            HSD: <fmt:formatDate value="${v.expired_date}" pattern="dd/MM/yyyy"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>