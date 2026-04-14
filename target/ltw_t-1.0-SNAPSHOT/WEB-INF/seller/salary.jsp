<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lương nhân viên – E·Fashion</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            background: #1a1a2e;
            color: #e0e0e0;
            font-family: 'Segoe UI', sans-serif;
            min-height: 100vh;
            padding: 32px 20px;
        }

        .container { max-width: 900px; margin: 0 auto; }

        .header {
            margin-bottom: 28px;
        }
        .header .brand {
            font-size: 12px;
            letter-spacing: 3px;
            color: #c9a96e;
            margin-bottom: 8px;
        }
        .header h1 {
            font-size: 26px;
            font-weight: 600;
        }
        .header p {
            font-size: 13px;
            color: #888;
            margin-top: 4px;
        }

        .back-btn {
            display: inline-block;
            margin-bottom: 24px;
            color: #c9a96e;
            text-decoration: none;
            font-size: 14px;
        }
        .back-btn:hover { text-decoration: underline; }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #16213e;
            border-radius: 12px;
            overflow: hidden;
        }

        thead tr {
            background: #0f3460;
        }
        thead th {
            padding: 14px 16px;
            text-align: left;
            font-size: 13px;
            font-weight: 600;
            color: #c9a96e;
            letter-spacing: 0.5px;
        }

        tbody tr {
            border-top: 1px solid #1e2a45;
            transition: background 0.2s;
        }
        tbody tr:hover { background: #1e2a45; }

        tbody td {
            padding: 14px 16px;
            font-size: 14px;
        }

        .amount { text-align: right; font-variant-numeric: tabular-nums; }

        .net-salary {
            font-weight: 700;
            color: #c9a96e;
        }

        .bonus-val { color: #4caf89; }
        .deduct-val { color: #e57373; }

        .badge {
            display: inline-block;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 12px;
            background: #0f3460;
            color: #90caf9;
        }

        .empty {
            text-align: center;
            padding: 48px;
            color: #666;
            font-size: 15px;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="header">
        <div class="brand">E · FASHION</div>
        <h1>Bảng lương nhân viên</h1>
        <p>
            <c:choose>
                <c:when test="${role == 'admin'}">Trang quản trị · Xem tất cả</c:when>
                <c:otherwise>Lương cá nhân của bạn</c:otherwise>
            </c:choose>
        </p>
    </div>

    <a class="back-btn" href="${pageContext.request.contextPath}/seller">← Quay lại trang chủ</a>

    <c:choose>
        <c:when test="${empty salaries}">
            <div class="empty">Chưa có dữ liệu lương.</div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <c:if test="${role == 'admin'}">
                            <th>Nhân viên</th>
                        </c:if>
                        <th>Tháng / Năm</th>
                        <th class="amount">Lương cơ bản</th>
                        <th class="amount">Thưởng</th>
                        <th class="amount">Khấu trừ</th>
                        <th class="amount">Thực nhận</th>
                        <th>Ghi chú</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${salaries}">
                        <tr>
                            <c:if test="${role == 'admin'}">
                                <td><span class="badge">${s.staffName}</span></td>
                            </c:if>
                            <td>Tháng ${s.month} / ${s.year}</td>
                            <td class="amount">
                                <fmt:formatNumber value="${s.baseSalary}" type="number"/>đ
                            </td>
                            <td class="amount bonus-val">
                                +<fmt:formatNumber value="${s.bonus}" type="number"/>đ
                            </td>
                            <td class="amount deduct-val">
                                -<fmt:formatNumber value="${s.deduction}" type="number"/>đ
                            </td>
                            <td class="amount net-salary">
                                <fmt:formatNumber value="${s.netSalary}" type="number"/>đ
                            </td>
                            <td>${s.note}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>