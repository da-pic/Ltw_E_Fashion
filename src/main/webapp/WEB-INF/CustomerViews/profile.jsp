<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hồ Sơ Của Tôi</title>
    <style>
        body { background-color: #f5f5f5; font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .css-toggle { display: none; }
        .container { max-width: 900px; margin: 0 auto; background-color: #fff; border-radius: 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }

        /* --- CSS THÔNG BÁO (ALERT) --- */
        .alert-container { padding: 0 30px; margin-top: 15px; }
        .alert { padding: 12px 20px; border-radius: 4px; font-size: 14px; margin-bottom: 10px; border: 1px solid transparent; }
        .alert-success { background-color: #e6fcf5; color: #0ca678; border-color: #c3fae8; }
        .alert-danger { background-color: #fff5f5; color: #fa5252; border-color: #ffe3e3; }

        /* --- HEADER --- */
        .profile-header { padding: 20px 30px; border-bottom: 1px solid #efefef; display: flex; justify-content: space-between; align-items: center; }
        .header-title-area h2 { margin: 0 0 5px 0; font-size: 18px; color: #333; }
        .header-title-area p { margin: 0; font-size: 14px; color: #555; }
        
        .btn-logout { background-color: #f5f5f5; color: #555; border: 1px solid #ddd; padding: 8px 20px; border-radius: 2px; text-decoration: none; font-size: 14px; font-weight: bold; transition: 0.2s; }
        .btn-logout:hover { background-color: #fff0ee; color: #ee4d2d; border-color: #ee4d2d; }

        .profile-body { display: flex; padding: 30px; }
        .profile-form { flex: 2; padding-right: 30px; border-right: 1px solid #efefef; }
        .form-row { display: flex; align-items: center; margin-bottom: 25px; }
        .form-label { width: 140px; text-align: right; padding-right: 20px; color: #555; font-size: 14px; }
        .form-input-ctn { flex: 1; font-size: 14px; color: #333; }
        .form-input-ctn input[type="text"], .form-input-ctn input[type="date"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 2px; box-sizing: border-box; }
        .btn-save { background-color: #ee4d2d; color: #fff; border: none; padding: 10px 25px; border-radius: 2px; cursor: pointer; font-size: 14px; margin-left: 160px; }
        .btn-save:hover { background-color: #d73d1f; }
        .profile-avatar { flex: 1; display: flex; flex-direction: column; align-items: center; }
        .avatar-circle { width: 100px; height: 100px; background-color: #efefef; border-radius: 50%; display: flex; justify-content: center; align-items: center; font-size: 40px; color: #ccc; margin-bottom: 15px; }
        .btn-upload { background-color: #fff; border: 1px solid #ccc; padding: 8px 15px; cursor: pointer; color: #555; font-size: 14px; border-radius: 2px; }

        .address-section { padding: 20px 30px 30px 30px; border-top: 10px solid #f5f5f5; }
        .address-header { display: flex; justify-content: space-between; align-items: center; }
        .btn-add-address { background-color: #fff; color: #ee4d2d; border: 1px solid #ee4d2d; padding: 8px 15px; border-radius: 2px; cursor: pointer; font-size: 14px; display: inline-block; }
        .address-form-wrapper { max-height: 0; overflow: hidden; transition: max-height 0.4s ease-out; background-color: #fafafa; border-radius: 4px; }
        #toggle-address:checked ~ .address-form-wrapper { max-height: 500px; margin-top: 15px; border: 1px solid #efefef; padding: 20px; }
        .grid-inputs { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .grid-inputs input, .grid-inputs textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 2px; box-sizing: border-box; }
        .grid-inputs textarea { grid-column: span 2; resize: vertical; }
        .back-link { display: inline-block; margin-bottom: 20px; color: #ee4d2d; text-decoration: none; font-weight: bold; }
        .address-list { margin-top: 20px; border-top: 1px solid #efefef; padding-top: 15px;}
        .saved-address-form { border: 1px solid #e8e8e8; padding: 20px; margin-bottom: 20px; border-radius: 4px; background-color: #fafafa; position: relative; }
        .address-field { display: flex; align-items: center; margin-bottom: 15px; }
        .address-field label { width: 110px; text-align: right; margin-right: 15px; color: #555; font-size: 14px; }
        .mock-disabled { flex: 1; padding: 10px; border: 1px solid #ddd; border-radius: 2px; background-color: #ebebeb; color: #777; pointer-events: none; }
        .saved-address-actions { text-align: right; border-top: 1px dashed #ddd; padding-top: 15px; margin-top: 5px; }
        .btn-action { padding: 8px 20px; font-size: 14px; border-radius: 2px; cursor: pointer; display: inline-block; border: 1px solid; }
        .btn-sua, .btn-huy { background-color: #fff; border-color: #ccc; color: #333; margin-right: 10px; }
        .btn-xoa { background-color: #fff; border-color: #ee4d2d; color: #ee4d2d; }
        .btn-luu { background-color: #ee4d2d; border-color: #ee4d2d; color: #fff; }
        .btn-huy, .btn-luu { display: none; }
        .edit-toggle-cb:checked ~ .grid-inputs .mock-disabled { background-color: #fff; color: #333; pointer-events: auto; border-color: #ccc; }
        .edit-toggle-cb:checked ~ .saved-address-actions .btn-sua, .edit-toggle-cb:checked ~ .saved-address-actions .btn-xoa { display: none; }
        .edit-toggle-cb:checked ~ .saved-address-actions .btn-huy, .edit-toggle-cb:checked ~ .saved-address-actions .btn-luu { display: inline-block; }
    </style>
</head>
<body>

    <a href="${pageContext.request.contextPath}/CustomerHome" class="back-link">← Quay lại trang chủ</a>

    <div class="container">
        <div class="profile-header">
            <div class="header-title-area">
                <h2>Hồ Sơ Của Tôi</h2>
                <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
            </div>
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Đăng Xuất</a>
        </div>

        <div class="alert-container">
            <%-- 1. Thông báo THÀNH CÔNG (từ tham số ?msg=...) --%>
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success">
                    <c:choose>
                        <c:when test="${param.msg == 'add_success'}">✨ Thêm địa chỉ mới thành công!</c:when>
                        <c:when test="${param.msg == 'update_success'}">✅ Đã cập nhật địa chỉ thành công!</c:when>
                        <c:when test="${param.msg == 'delete_success'}">🗑️ Đã xóa địa chỉ thành công!</c:when>
                        <c:otherwise>Thao tác thực hiện thành công!</c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <%-- 2. Thông báo THẤT BẠI (từ tham số ?error=... HOẶC request attribute) --%>
            <c:set var="errorMsg" value="${not empty param.error ? param.error : error}" />
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger">
                    <strong>Lỗi:</strong> 
                    <c:choose>
                        <c:when test="${errorMsg == 'add_failed'}">Không thể thêm địa chỉ. Vui lòng kiểm tra lại.</c:when>
                        <c:when test="${errorMsg == 'update_failed'}">Cập nhật thất bại. Dữ liệu không hợp lệ.</c:when>
                        <c:when test="${errorMsg == 'not_found'}">Không tìm thấy địa chỉ yêu cầu.</c:when>
                        <c:when test="${errorMsg == 'unauthorized'}">Phiên làm việc hết hạn. Vui lòng đăng nhập lại.</c:when>
                        <c:otherwise>${errorMsg}</c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
        <form action="${pageContext.request.contextPath}/UpdateProfile" method="POST" class="profile-body">
            <div class="profile-form">
                <div class="form-row">
                    <div class="form-label">Tên đăng nhập</div>
                    <div class="form-input-ctn"><strong>${sessionScope.currentUser.username}</strong></div>
                </div>
                <div class="form-row">
                    <div class="form-label">Tên</div>
                    <div class="form-input-ctn"><input type="text" name="name" value="${sessionScope.currentUser.name}"></div>
                </div>
                <div class="form-row">
                    <div class="form-label">Số điện thoại</div>
                    <div class="form-input-ctn"><strong>${sessionScope.currentUser.phonenumber}</strong></div>
                </div>
                <div class="form-row">
                    <div class="form-label">Giới tính</div>
                    <div class="form-input-ctn radio-group">
                        <label><input type="radio" name="gender" value="Male" ${sessionScope.currentUser.gender == 'Male' ? 'checked' : ''}> Nam</label>
                        <label><input type="radio" name="gender" value="Female" ${sessionScope.currentUser.gender == 'Female' ? 'checked' : ''}> Nữ</label>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-label">Ngày sinh</div>
                    <div class="form-input-ctn"><input type="date" name="birthdate" value="${sessionScope.currentUser.birthdate}"></div>
                </div>
                <button type="submit" class="btn-save">Lưu</button>
            </div>
            <div class="profile-avatar">
                <div class="avatar-circle">👤</div>
                <label class="btn-upload">Chọn Ảnh</label>
            </div>
        </form>

        <div class="address-section">
            <div class="address-header">
                <h3 style="margin: 0; font-size: 16px; color: #333;">Địa Chỉ Của Tôi</h3>
                <label for="toggle-address" class="btn-add-address">+ Thêm địa chỉ mới</label>
            </div>
            
            <div class="address-list">
                <c:choose>
                    <c:when test="${empty addressList}">
                        <div style="text-align: center; padding: 30px; color: #888; font-size: 14px;">Bạn chưa có địa chỉ giao hàng nào.</div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="addr" items="${addressList}">
                            <form method="POST" class="saved-address-form">
                                <input type="hidden" name="addressId" value="${addr.id}">
                                <input type="checkbox" id="edit-toggle-${addr.id}" class="css-toggle edit-toggle-cb">
                                
                                <div class="grid-inputs">
                                    <div class="address-field">
                                        <label>Số điện thoại</label>
                                        <input type="text" name="phone_number" value="${addr.phone_number}" class="mock-disabled">
                                    </div>
                                    <div class="address-field">
                                        <label>Đường / Phố</label>
                                        <input type="text" name="street" value="${addr.street}" class="mock-disabled">
                                    </div>
                                    <div class="address-field">
                                        <label>Phường / Xã</label>
                                        <input type="text" name="ward" value="${addr.ward}" class="mock-disabled">
                                    </div>
                                    <div class="address-field">
                                        <label>Quận / Huyện</label>
                                        <input type="text" name="district" value="${addr.district}" class="mock-disabled">
                                    </div>
                                    <div style="grid-column: span 2;" class="address-field">
                                        <label>Tỉnh / TP</label>
                                        <input type="text" name="city" value="${addr.city}" class="mock-disabled">
                                    </div>
                                    <div style="grid-column: span 2;" class="address-field">
                                        <label>Chi tiết thêm</label>
                                        <textarea name="detail" rows="2" class="mock-disabled">${addr.detail}</textarea>
                                    </div>
                                </div>
                                
                                <div class="saved-address-actions">
                                    <label for="edit-toggle-${addr.id}" class="btn-action btn-sua">Sửa</label>
                                    <button type="submit" formaction="${pageContext.request.contextPath}/address/delete" class="btn-action btn-xoa">Xóa</button>
                                    <label for="edit-toggle-${addr.id}" class="btn-action btn-huy">Hủy</label>
                                    <button type="submit" formaction="${pageContext.request.contextPath}/address/update" class="btn-action btn-luu">Lưu Thay Đổi</button>
                                </div>
                            </form>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <input type="checkbox" id="toggle-address" class="css-toggle">
            <form action="${pageContext.request.contextPath}/address/add" method="POST" class="address-form-wrapper">
                <div class="grid-inputs">
                    <input type="text" name="phone_number" required placeholder="Số điện thoại">
                    <input type="text" name="city" required placeholder="Tỉnh / Thành phố">
                    <input type="text" name="district" required placeholder="Quận / Huyện">
                    <input type="text" name="ward" required placeholder="Phường / Xã">
                    <input type="text" name="street" placeholder="Đường / Phố">
                    <textarea name="detail" rows="2" placeholder="Chi tiết thêm"></textarea>
                </div>
                <div style="text-align: right; margin-top: 15px;">
                    <label for="toggle-address" style="cursor: pointer; margin-right: 15px; color: #555;">Hủy</label>
                    <button type="submit" class="btn-save" style="margin-left: 0;">Hoàn thành</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>