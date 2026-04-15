<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch Sử Đơn Hàng</title>
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 4px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
        .header-title { color: #ee4d2d; border-bottom: 2px solid #eee; padding-bottom: 15px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center;}
        
        /* HIỂN THỊ THÔNG BÁO */
        .alert { padding: 12px 15px; border-radius: 4px; margin-bottom: 20px; font-size: 14px; font-weight: bold; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-danger { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }

        /* CHỐNG TRÀN BẢNG */
        .table-history { 
            width: 100%; 
            border-collapse: collapse; 
            table-layout: fixed; 
        }
        .table-history th { background-color: #fafafa; padding: 12px; text-align: left; border-bottom: 1px solid #ddd; color: #555; }
        
        /* CHỐNG TRÀN CHỮ TRONG Ô */
        .table-history td { 
            padding: 15px 12px; 
            border-bottom: 1px solid #eee; 
            color: #333; 
            word-wrap: break-word; 
            vertical-align: middle;
        }

        /* Set độ rộng cố định cho từng cột cho cân đối (Tổng = 100%) */
        .col-id { width: 12%; }
        .col-date { width: 13%; }
        .col-address { width: 30%; }
        .col-payment { width: 13%; }
        .col-total { width: 12%; }
        .col-status { width: 10%; }
        .col-action { width: 10%; text-align: center; } /* Cột mới thêm */

        .status-badge { padding: 4px 10px; border-radius: 20px; font-size: 12px; font-weight: bold; color: #fff; display: inline-block; text-align: center;}
        .status-pending { background-color: #f39c12; }
        .status-shipping { background-color: #3498db; }
        .status-delivered { background-color: #2ecc71; }
        .status-canceled { background-color: #e74c3c; }
        .total-price { color: #ee4d2d; font-weight: bold; }

        /* Nút Hủy Đơn */
        .btn-cancel {
            background-color: #fff;
            color: #e74c3c;
            border: 1px solid #e74c3c;
            padding: 6px 12px;
            border-radius: 3px;
            cursor: pointer;
            font-size: 12px;
            font-weight: bold;
            transition: 0.2s;
            width: 100%;
        }
        .btn-cancel:hover { background-color: #e74c3c; color: #fff; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header-title">
            <h2 style="margin: 0;">📦 LỊCH SỬ ĐƠN HÀNG</h2>
            <a href="${pageContext.request.contextPath}/CustomerHome" style="color: #555; text-decoration: none; font-weight: bold;">← Quay lại Trang chủ</a>
        </div>

        <c:if test="${param.msg == 'cancel_success'}">
            <div class="alert alert-success">✔️ Đã hủy đơn hàng và hoàn lại mã giảm giá thành công!</div>
        </c:if>
        <c:if test="${param.error == 'cancel_failed'}">
            <div class="alert alert-danger">❌ Hủy đơn thất bại. Đơn hàng đang được giao hoặc không hợp lệ.</div>
        </c:if>

        <c:choose>
            <c:when test="${empty orders}">
                <p style="text-align: center; color: #777; padding: 40px 0; font-size: 16px;">Bạn chưa có đơn hàng nào.</p>
            </c:when>
            <c:otherwise>
                <table class="table-history">
                    <thead>
                        <tr>
                            <th class="col-id">Mã Đơn</th>
                            <th class="col-date">Ngày Đặt</th>
                            <th class="col-address">Địa Chỉ Nhận</th>
                            <th class="col-payment">Thanh Toán</th>
                            <th class="col-total">Tổng Tiền</th>
                            <th class="col-status">Trạng Thái</th>
                            <th class="col-action">Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td style="font-family: monospace; font-size: 14px; font-weight: bold; color: #0056b3;" title="${order.id}">
                                    #${order.id.substring(0, 8).toUpperCase()}
                                </td>
                                
                                <td><fmt:formatDate value="${order.created_at}" pattern="dd/MM/yyyy HH:mm"/></td>
                                
                                <td>
                                    <div style="font-weight: bold; margin-bottom: 4px; font-size: 13px;">SĐT: ${order.phone_number}</div>
                                    <div style="color: #555; font-size: 13px; line-height: 1.4;">${order.full_address}</div>
                                </td>

                                <td style="font-size: 13px;">${order.payment_method == 'cash' ? 'Tiền mặt (COD)' : 'Chuyển khoản'}</td>
                                <td class="total-price">${order.total_price}đ</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${order.order_status == 'pending'}"><span class="status-badge status-pending">Chờ xử lý</span></c:when>
                                        <c:when test="${order.order_status == 'shipping'}"><span class="status-badge status-shipping">Đang giao</span></c:when>
                                        <c:when test="${order.order_status == 'delivered'}"><span class="status-badge status-delivered">Đã giao</span></c:when>
                                        <c:otherwise><span class="status-badge status-canceled">Đã hủy</span></c:otherwise>
                                    </c:choose>
                                </td>

                                <td style="text-align: center;">
                                    <c:if test="${order.order_status == 'pending'}">
                                        <form action="${pageContext.request.contextPath}/order-cancel" method="POST" 
                                              onsubmit="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này? Mã giảm giá (nếu có) sẽ được hoàn lại vào ví.');">
                                            <input type="hidden" name="orderId" value="${order.id}">
                                            <button type="submit" class="btn-cancel">Hủy đơn</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose> 
    </div>
</body>
</html>