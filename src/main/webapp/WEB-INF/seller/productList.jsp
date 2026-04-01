<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Sản phẩm</title>
    <link href="https://fonts.googleapis.com/css2?family=Sora:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg: #0f1117;
            --surface: #1a1d27;
            --surface2: #22263a;
            --border: #2e3250;
            --accent: #6c63ff;
            --accent2: #ff6584;
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

        /* ── HEADER ── */
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
        .logo {
            font-size: 18px;
            font-weight: 700;
            letter-spacing: -0.5px;
            color: var(--text);
        }
        .logo span { color: var(--accent); }
        .header-meta { font-size: 13px; color: var(--text-muted); }

        /* ── MAIN ── */
        .main { max-width: 1200px; margin: 0 auto; padding: 40px 24px; }

        .page-top {
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
            margin-bottom: 32px;
        }
        .page-title {
            font-size: 28px;
            font-weight: 700;
            letter-spacing: -0.8px;
            line-height: 1;
        }
        .page-title small {
            display: block;
            font-size: 13px;
            font-weight: 400;
            color: var(--text-muted);
            margin-top: 6px;
            letter-spacing: 0;
        }

        /* ── TOAST ── */
        .toast {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 12px 18px;
            border-radius: 10px;
            font-size: 13px;
            font-weight: 500;
            margin-bottom: 24px;
            animation: slideIn .3s ease;
        }
        .toast-success {
            background: rgba(67, 217, 173, 0.12);
            border: 1px solid rgba(67, 217, 173, 0.3);
            color: var(--success);
        }
        @keyframes slideIn {
            from { opacity: 0; transform: translateY(-8px); }
            to   { opacity: 1; transform: translateY(0); }
        }

        /* ── STATS ROW ── */
        .stats-row {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 16px;
            margin-bottom: 28px;
        }
        .stat-card {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 12px;
            padding: 20px 24px;
        }
        .stat-label {
            font-size: 11px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: var(--text-muted);
            margin-bottom: 8px;
        }
        .stat-value { font-size: 28px; font-weight: 700; letter-spacing: -1px; }
        .stat-value.accent  { color: var(--accent); }
        .stat-value.success { color: var(--success); }
        .stat-value.danger  { color: var(--danger); }

        /* ── TOOLBAR ── */
        .toolbar { display: flex; align-items: center; gap: 12px; margin-bottom: 20px; }
        .search-wrap { position: relative; flex: 1; }
        .search-icon {
            position: absolute; left: 14px; top: 50%; transform: translateY(-50%);
            color: var(--text-muted); font-size: 15px; pointer-events: none;
        }
        .search-input {
            width: 100%;
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 8px;
            color: var(--text);
            font-family: 'Sora', sans-serif;
            font-size: 14px;
            padding: 10px 14px 10px 40px;
            outline: none;
            transition: border-color .2s;
        }
        .search-input::placeholder { color: var(--text-muted); }
        .search-input:focus { border-color: var(--accent); }

        /* ── TABLE ── */
        .table-wrap {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 14px;
            overflow: hidden;
        }
        table { width: 100%; border-collapse: collapse; }
        thead { background: var(--surface2); }
        th {
            padding: 14px 20px;
            font-size: 11px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: var(--text-muted);
            text-align: left;
            border-bottom: 1px solid var(--border);
        }
        td {
            padding: 16px 20px;
            font-size: 14px;
            border-bottom: 1px solid var(--border);
            vertical-align: middle;
        }
        tbody tr:last-child td { border-bottom: none; }
        tbody tr { transition: background .15s; }
        tbody tr:hover { background: var(--surface2); }

        .id-chip {
            font-size: 12px; font-weight: 600; font-family: monospace;
            color: var(--text-muted);
            background: var(--surface2);
            border: 1px solid var(--border);
            padding: 3px 8px; border-radius: 5px;
        }
        .product-name { font-weight: 600; color: var(--text); }
        .tag {
            display: inline-flex; align-items: center;
            font-size: 12px; font-weight: 500;
            padding: 3px 10px; border-radius: 20px;
            background: rgba(108, 99, 255, 0.12);
            border: 1px solid rgba(108, 99, 255, 0.25);
            color: #a99fff;
        }
        .badge {
            display: inline-flex; align-items: center; gap: 5px;
            font-size: 12px; font-weight: 600;
            padding: 4px 12px; border-radius: 20px;
        }
        .badge::before { content: ''; width: 6px; height: 6px; border-radius: 50%; }
        .badge-on {
            background: rgba(67,217,173,.1); border: 1px solid rgba(67,217,173,.25);
            color: var(--success);
        }
        .badge-on::before  { background: var(--success); }
        .badge-off {
            background: rgba(255,101,132,.1); border: 1px solid rgba(255,101,132,.25);
            color: var(--danger);
        }
        .badge-off::before { background: var(--danger); }
        .desc {
            color: var(--text-muted); font-size: 13px;
            max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
        }

        /* ── ACTION BUTTONS ── */
        .actions { display: flex; gap: 8px; }
        .btn-icon {
            display: inline-flex; align-items: center; gap: 5px;
            padding: 7px 14px; border-radius: 7px;
            font-family: 'Sora', sans-serif; font-size: 12px; font-weight: 600;
            text-decoration: none; border: 1px solid transparent;
            cursor: pointer; transition: all .2s;
        }
        .btn-edit {
            background: rgba(108,99,255,.12); border-color: rgba(108,99,255,.3); color: #a99fff;
        }
        .btn-edit:hover { background: rgba(108,99,255,.25); border-color: var(--accent); }
        .btn-del {
            background: rgba(255,101,132,.08); border-color: rgba(255,101,132,.2); color: #ff8da8;
        }
        .btn-del:hover { background: rgba(255,101,132,.2); border-color: var(--danger); }

        /* ── EMPTY STATE ── */
        .empty { text-align: center; padding: 60px 20px; color: var(--text-muted); }
        .empty-icon { font-size: 40px; margin-bottom: 12px; }
        .empty p { font-size: 14px; }

        /* ── MODAL ── */
        .overlay {
            display: none; position: fixed; inset: 0;
            background: rgba(0,0,0,.6); backdrop-filter: blur(4px);
            z-index: 200; align-items: center; justify-content: center;
        }
        .overlay.show { display: flex; }
        .modal {
            background: var(--surface); border: 1px solid var(--border);
            border-radius: 16px; padding: 32px; width: 380px;
            animation: popIn .25s ease;
        }
        @keyframes popIn {
            from { opacity: 0; transform: scale(.94); }
            to   { opacity: 1; transform: scale(1); }
        }
        .modal-icon { font-size: 36px; margin-bottom: 14px; }
        .modal h3  { font-size: 18px; font-weight: 700; margin-bottom: 8px; }
        .modal p   { font-size: 14px; color: var(--text-muted); line-height: 1.6; }
        .modal-actions { display: flex; gap: 10px; margin-top: 24px; }
        .modal-cancel {
            flex: 1; padding: 11px; background: var(--surface2);
            border: 1px solid var(--border); border-radius: 8px;
            color: var(--text); font-family: 'Sora', sans-serif;
            font-size: 14px; font-weight: 600; cursor: pointer; transition: background .2s;
        }
        .modal-cancel:hover { background: var(--border); }
        .modal-confirm {
            flex: 1; padding: 11px; background: var(--danger);
            border: none; border-radius: 8px; color: #fff;
            font-family: 'Sora', sans-serif; font-size: 14px; font-weight: 600;
            cursor: pointer; opacity: .9; transition: opacity .2s;
        }
        .modal-confirm:hover { opacity: 1; }
    </style>
</head>
<body>

<header class="header">
    <div class="logo">seller<span>.</span>panel</div>
    <div class="header-meta">Quản lý Sản phẩm</div>
</header>

<main class="main">

    <c:if test="${param.msg == 'deleted'}">
        <div class="toast toast-success">✓ &nbsp;Đã vô hiệu hóa sản phẩm thành công.</div>
    </c:if>
    <c:if test="${param.msg == 'updated'}">
        <div class="toast toast-success">✓ &nbsp;Cập nhật sản phẩm thành công.</div>
    </c:if>

    <div class="page-top">
        <div class="page-title">
            Sản phẩm
            <small>Xem, chỉnh sửa và quản lý toàn bộ sản phẩm</small>
        </div>
    </div>

    <div class="stats-row">
        <div class="stat-card">
            <div class="stat-label">Tổng sản phẩm</div>
            <div class="stat-value accent" id="statTotal">0</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Đang hoạt động</div>
            <div class="stat-value success" id="statActive">0</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Đã ẩn</div>
            <div class="stat-value danger" id="statInactive">0</div>
        </div>
    </div>

    <div class="toolbar">
        <div class="search-wrap">
            <span class="search-icon">⌕</span>
            <input class="search-input" type="text" id="searchInput"
                   placeholder="Tìm theo tên, ID, thương hiệu..."/>
        </div>
    </div>

    <div class="table-wrap">
        <table id="productTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sản phẩm</th>
                    <th>Thương hiệu</th>
                    <th>Danh mục</th>
                    <th>Trạng thái</th>
                    <th>Mô tả</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td><span class="id-chip">${p.id}</span></td>
                        <td><span class="product-name">${p.productName}</span></td>
                        <td><span class="tag">${p.brandId}</span></td>
                        <td><span class="tag">${p.categoryId}</span></td>
                        <td>
                            <c:choose>
                                <c:when test="${p.isActive == 1}">
                                    <span class="badge badge-on">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-off">Đã ẩn</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><span class="desc" title="${p.description}">${p.description}</span></td>
                        <td>
                            <div class="actions">
                                <a href="${pageContext.request.contextPath}/seller/product?action=edit&id=${p.id}"
                                   class="btn-icon btn-edit">✏ Sửa</a>
                                <a href="#" class="btn-icon btn-del"
                                   onclick="confirmDelete('${p.id}', '${p.productName}'); return false;">
                                   🗑 Xóa</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty products}">
            <div class="empty">
                <div class="empty-icon">📦</div>
                <p>Chưa có sản phẩm nào.</p>
            </div>
        </c:if>
    </div>
</main>

<!-- DELETE MODAL -->
<div class="overlay" id="deleteOverlay">
    <div class="modal">
        <div class="modal-icon">🗑️</div>
        <h3>Xác nhận vô hiệu hóa</h3>
        <p>Sản phẩm <strong id="modalProductName"></strong> sẽ bị ẩn khỏi hệ thống.<br>Bạn có chắc muốn tiếp tục?</p>
        <div class="modal-actions">
            <button class="modal-cancel" onclick="closeModal()">Hủy bỏ</button>
            <button class="modal-confirm" id="confirmBtn">Vô hiệu hóa</button>
        </div>
    </div>
</div>

<script>
    const rows = document.querySelectorAll('#productTable tbody tr');
    let active = 0, inactive = 0;
    rows.forEach(r => {
        if (r.querySelector('.badge-on'))  active++;
        if (r.querySelector('.badge-off')) inactive++;
    });
    document.getElementById('statTotal').textContent    = rows.length;
    document.getElementById('statActive').textContent   = active;
    document.getElementById('statInactive').textContent = inactive;

    document.getElementById('searchInput').addEventListener('input', function () {
        const q = this.value.toLowerCase();
        rows.forEach(r => {
            r.style.display = r.textContent.toLowerCase().includes(q) ? '' : 'none';
        });
    });

    let deleteUrl = '';
    function confirmDelete(id, name) {
        deleteUrl = '${pageContext.request.contextPath}/seller/product?action=delete&id=' + id;
        document.getElementById('modalProductName').textContent = name;
        document.getElementById('deleteOverlay').classList.add('show');
    }
    function closeModal() {
        document.getElementById('deleteOverlay').classList.remove('show');
    }
    document.getElementById('confirmBtn').addEventListener('click', () => {
        window.location.href = deleteUrl;
    });
    document.getElementById('deleteOverlay').addEventListener('click', function (e) {
        if (e.target === this) closeModal();
    });
</script>
</body>
</html>
