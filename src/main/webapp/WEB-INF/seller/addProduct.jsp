<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Sản phẩm</title>
    <link href="https://fonts.googleapis.com/css2?family=Sora:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg: #0f1117;
            --surface: #1a1d27;
            --surface2: #22263a;
            --border: #2e3250;
            --accent: #6c63ff;
            --text: #e8eaf6;
            --text-muted: #7b80a0;
            --success: #43d9ad;
            --danger: #ff6584;
        }

        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: 'Sora', sans-serif;
            background: var(--bg);
            color: var(--text);
            min-height: 100vh;
        }

        .header {
            background: var(--surface);
            border-bottom: 1px solid var(--border);
            padding: 0 40px;
            height: 64px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            position: sticky;
            top: 0;
            z-index: 100;
        }
        .logo { font-size: 18px; font-weight: 700; letter-spacing: -0.5px; }
        .logo span { color: var(--accent); }
        .back-btn {
            display: flex; align-items: center; gap: 7px;
            color: var(--text-muted); text-decoration: none;
            font-size: 13px; font-weight: 500;
            padding: 7px 14px; border-radius: 7px;
            border: 1px solid var(--border);
            transition: all .2s;
        }
        .back-btn:hover { color: var(--text); border-color: var(--text-muted); }

        .main {
            max-width: 640px;
            margin: 48px auto;
            padding: 0 24px;
        }

        .page-title {
            font-size: 26px; font-weight: 700;
            letter-spacing: -0.8px; margin-bottom: 8px;
        }
        .page-sub {
            font-size: 13px; color: var(--text-muted); margin-bottom: 32px;
        }

        .card {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: 36px;
        }

        .alert-error {
            display: flex; align-items: center; gap: 10px;
            padding: 12px 16px;
            background: rgba(255,101,132,.1);
            border: 1px solid rgba(255,101,132,.3);
            border-radius: 8px; color: var(--danger);
            font-size: 13px; margin-bottom: 24px;
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 18px;
        }
        .form-group { display: flex; flex-direction: column; gap: 7px; }
        .form-group.full { grid-column: 1 / -1; }

        label {
            font-size: 12px; font-weight: 600;
            text-transform: uppercase; letter-spacing: .8px;
            color: var(--text-muted);
        }
        label .req { color: var(--danger); margin-left: 2px; }

        input[type="text"], textarea {
            background: var(--surface2);
            border: 1px solid var(--border);
            border-radius: 8px;
            color: var(--text);
            font-family: 'Sora', sans-serif;
            font-size: 14px;
            padding: 11px 14px;
            outline: none;
            transition: border-color .2s;
        }
        input[type="text"]:focus, textarea:focus {
            border-color: var(--accent);
        }
        input::placeholder, textarea::placeholder { color: var(--text-muted); }
        textarea { resize: vertical; min-height: 90px; }

        .hint { font-size: 11px; color: var(--text-muted); margin-top: 2px; }

        .form-actions {
            display: flex; gap: 12px; margin-top: 28px;
        }
        .btn-submit {
            flex: 1; padding: 13px;
            background: var(--accent);
            border: none; border-radius: 9px;
            color: #fff; font-family: 'Sora', sans-serif;
            font-size: 14px; font-weight: 600;
            cursor: pointer; transition: opacity .2s;
        }
        .btn-submit:hover { opacity: .88; }
        .btn-cancel {
            padding: 13px 24px;
            background: var(--surface2);
            border: 1px solid var(--border);
            border-radius: 9px; color: var(--text);
            font-family: 'Sora', sans-serif;
            font-size: 14px; font-weight: 600;
            text-decoration: none;
            display: flex; align-items: center;
            transition: background .2s;
        }
        .btn-cancel:hover { background: var(--border); }
    </style>
</head>
<body>

<header class="header">
    <div class="logo">seller<span>.</span>panel</div>
    <a href="${pageContext.request.contextPath}/seller/product" class="back-btn">
        ← Danh sách sản phẩm
    </a>
</header>

<main class="main">
    <div class="page-title">Thêm Sản phẩm</div>
    <div class="page-sub">Điền đầy đủ thông tin để thêm sản phẩm mới vào hệ thống</div>

    <div class="card">
        <c:if test="${not empty error}">
            <div class="alert-error">⚠ ${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/seller/product">
            <input type="hidden" name="action" value="add"/>

            <div class="form-grid">
                <div class="form-group">
                    <label>ID Sản phẩm <span class="req">*</span></label>
                    <input type="text" name="id"
                           value="${product.id}"
                           placeholder="VD: prd-08" required/>
                    <span class="hint">ID phải duy nhất, không trùng lặp</span>
                </div>

                <div class="form-group">
                    <label>Tên sản phẩm <span class="req">*</span></label>
                    <input type="text" name="productName"
                           value="${product.productName}"
                           placeholder="VD: iPhone 16 Pro" required/>
                </div>

                <div class="form-group">
                    <label>Thương hiệu (Brand ID)</label>
                    <input type="text" name="brandId"
                           value="${product.brandId}"
                           placeholder="VD: brd-01"/>
                </div>

                <div class="form-group">
                    <label>Danh mục (Category ID)</label>
                    <input type="text" name="categoryId"
                           value="${product.categoryId}"
                           placeholder="VD: 4"/>
                </div>

                <div class="form-group full">
                    <label>Mô tả sản phẩm</label>
                    <textarea name="description"
                              placeholder="Nhập mô tả ngắn về sản phẩm...">${product.description}</textarea>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">+ Thêm sản phẩm</button>
                <a href="${pageContext.request.contextPath}/seller/product" class="btn-cancel">Hủy</a>
            </div>
        </form>
    </div>
</main>

</body>
</html>
