<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");

    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    String staffName = currentUser.getName();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@300;400;600&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --bg: #0e0e0f;
            --card: #1a1a1d;
            --border: #2a2a2f;
            --gold: #c9a96e;
            --text: #f0ece4;
            --muted: #7a7770;
            --green: #4caf87;
            --red: #e07065;
            --blue: #6a9fd8;
        }

        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'DM Sans', sans-serif;
            background: var(--bg);
            color: var(--text);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 20px;
        }

        .container {
            width: 520px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: 40px;
            animation: fadeUp 0.5s ease both;
        }

        .logo {
            font-family: 'Cormorant Garamond', serif;
            font-size: 14px;
            letter-spacing: 4px;
            text-transform: uppercase;
            color: var(--gold);
            margin-bottom: 32px;
        }

        h2 {
            font-family: 'Cormorant Garamond', serif;
            font-size: 28px;
            font-weight: 300;
            color: var(--text);
            margin-bottom: 6px;
        }

        h2 span { color: var(--gold); font-weight: 600; }

        .subtitle {
            font-size: 12px;
            color: var(--muted);
            letter-spacing: 1px;
            margin-bottom: 32px;
        }

        .divider {
            height: 1px;
            background: var(--border);
            margin-bottom: 28px;
        }

        ul {
            list-style: none;
            padding: 0;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        li a {
            display: flex;
            align-items: center;
            gap: 14px;
            padding: 14px 18px;
            background: rgba(255,255,255,0.02);
            border: 1px solid var(--border);
            border-radius: 10px;
            text-decoration: none;
            color: var(--text);
            font-size: 13.5px;
            transition: all 0.2s;
        }

        li a:hover {
            background: rgba(201,169,110,0.08);
            border-color: var(--gold);
            color: var(--gold);
        }

        li a .icon {
            width: 34px;
            height: 34px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 13px;
            flex-shrink: 0;
        }

        li:nth-child(1) a .icon { background: rgba(201,169,110,0.12); color: var(--gold); }
        li:nth-child(2) a .icon { background: rgba(106,159,216,0.12); color: var(--blue); }
        li:nth-child(3) a .icon { background: rgba(76,175,135,0.12); color: var(--green); }
        li:nth-child(4) a .icon { background: rgba(224,112,101,0.12); color: var(--red); }

        li a .arrow {
            margin-left: auto;
            color: var(--muted);
            font-size: 11px;
        }

        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(16px); }
            to   { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="container">
    <div class="logo">E · Fashion</div>

    <h2>Xin chào, <span><%= staffName %></span> 👋</h2>
    <p class="subtitle">Trang người bán &nbsp;·&nbsp; Staff Portal</p>

    <div class="divider"></div>

    <ul>
        <li>
            <a href="<%= request.getContextPath() %>/seller/product?action=add">
                <div class="icon"><i class="fa-solid fa-plus"></i></div>
                Thêm sản phẩm
                <i class="fa-solid fa-chevron-right arrow"></i>
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/seller/product">
                <div class="icon"><i class="fa-solid fa-shirt"></i></div>
                Quản lý sản phẩm
                <i class="fa-solid fa-chevron-right arrow"></i>
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/seller/order-success">
                <div class="icon"><i class="fa-solid fa-bag-shopping"></i></div>
                Đơn hàng
                <i class="fa-solid fa-chevron-right arrow"></i>
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/seller/checkout">
                <div class="icon"><i class="fa-solid fa-bag-shopping"></i></div>
                Thanh toán
                <i class="fa-sold fa-chevron-right arrow"></i>
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/login">
                <div class="icon"><i class="fa-solid fa-arrow-right-from-bracket"></i></div>
                Đăng xuất
                <i class="fa-solid fa-chevron-right arrow"></i>
            </a>
        </li>
    </ul>
</div>

</body>
</html>
