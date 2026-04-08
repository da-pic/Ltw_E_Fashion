<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hồ Sơ Của Tôi</title>
    <style>
        body {
            background-color: #f5f5f5;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }

        .css-toggle { display: none; }

        .container {
            max-width: 900px;
            margin: 0 auto;
            background-color: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }

        /* ================= LAYOUT HỒ SƠ ================= */
        .profile-header {
            padding: 20px 30px;
            border-bottom: 1px solid #efefef;
        }
        .profile-header h2 { margin: 0 0 5px 0; font-size: 18px; color: #333; }
        .profile-header p { margin: 0; font-size: 14px; color: #555; }

        .profile-body { display: flex; padding: 30px; }

        .profile-form {
            flex: 2;
            padding-right: 30px;
            border-right: 1px solid #efefef;
        }

        .form-row { display: flex; align-items: center; margin-bottom: 25px; }
        .form-label { width: 140px; text-align: right; padding-right: 20px; color: #555; font-size: 14px; }
        .form-input-ctn { flex: 1; font-size: 14px; color: #333; }

        .form-input-ctn input[type="text"], .form-input-ctn input[type="date"] {
            width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 2px; box-sizing: border-box;
        }

        .text-muted { color: #888; font-size: 12px; margin-top: 5px; }
        .radio-group label { margin-right: 15px; cursor: pointer; color: #333;}

        .btn-save {
            background-color: #ee4d2d; color: #fff; border: none; padding: 10px 25px;
            border-radius: 2px; cursor: pointer; font-size: 14px; margin-left: 160px;
        }
        .btn-save:hover { background-color: #d73d1f; }

        .profile-avatar { flex: 1; display: flex; flex-direction: column; align-items: center; }
        .avatar-circle {
            width: 100px; height: 100px; background-color: #efefef; border-radius: 50%;
            display: flex; justify-content: center; align-items: center; font-size: 40px; color: #ccc; margin-bottom: 15px;
        }
        .btn-upload {
            background-color: #fff; border: 1px solid #ccc; padding: 8px 15px;
            cursor: pointer; color: #555; font-size: 14px; border-radius: 2px;
        }

        /* ================= PHẦN ĐỊA CHỈ ================= */
        .address-section { padding: 20px 30px 30px 30px; border-top: 10px solid #f5f5f5; }
        
        .address-header {
            display: flex; justify-content: space-between; align-items: center;
        }

        .btn-add-address {
            background-color: #fff; color: #ee4d2d; border: 1px solid #ee4d2d;
            padding: 8px 15px; border-radius: 2px; cursor: pointer; font-size: 14px; display: inline-block;
        }

        .address-form-wrapper {
            max-height: 0; overflow: hidden; transition: max-height 0.4s ease-out, margin-top 0.4s ease;
            background-color: #fafafa; border-radius: 4px;
        }

        #toggle-address:checked ~ .address-form-wrapper {
            max-height: 500px; margin-top: 15px; border: 1px solid #efefef; padding: 20px;
        }

        .grid-inputs { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .grid-inputs input, .grid-inputs textarea {
            width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 2px; box-sizing: border-box;
        }
        .grid-inputs textarea { grid-column: span 2; resize: vertical; }
        
        .back-link {
            display: inline-block; margin-bottom: 20px; color: #ee4d2d; text-decoration: none; font-weight: bold;
        }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>

    <a href="${pageContext.request.contextPath}/CustomerHome" class="back-link">← Quay lại trang chủ</a>

    <div class="container">
        <div class="profile-header">
            <h2>Hồ Sơ Của Tôi</h2>
            <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
        </div>
        
        <form action="${pageContext.request.contextPath}/update-profile" method="POST" class="profile-body">
            <div class="profile-form">
                <div class="form-row">
                    <div class="form-label">Tên đăng nhập</div>
                    <div class="form-input-ctn">
                        <strong>${sessionScope.currentUser.username}</strong>
                        <div class="text-muted">Tên Đăng nhập chỉ có thể thay đổi một lần.</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-label">Tên</div>
                    <div class="form-input-ctn">
                        <input type="text" name="name" value="${sessionScope.currentUser.name}">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-label">Số điện thoại</div>
                    <div class="form-input-ctn">
                        <input type="text" name="phonenumber" value="${sessionScope.currentUser.phonenumber}">
                    </div>
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
                    <div class="form-input-ctn">
                        <input type="date" name="birthdate" value="${sessionScope.currentUser.birthdate}">
                    </div>
                </div>

                <button type="submit" class="btn-save">Lưu</button>
            </div>

            <div class="profile-avatar">
                <div class="avatar-circle">👤</div>
                <label class="btn-upload">Chọn Ảnh</label>
                <div class="text-muted" style="text-align: center; margin-top: 15px; line-height: 1.5;">
                    Dung lượng file tối đa 1 MB<br>Định dạng: .JPEG, .PNG
                </div>
            </div>
        </form>

        <div class="address-section">
            <div class="address-header">
                <h3 style="margin: 0; font-size: 16px; color: #333;">Địa Chỉ Của Tôi</h3>
                <label for="toggle-address" class="btn-add-address">+ Thêm địa chỉ mới</label>
            </div>
            
            <input type="checkbox" id="toggle-address" class="css-toggle">
            
            <form action="${pageContext.request.contextPath}/add-address" method="POST" class="address-form-wrapper">
                <div class="grid-inputs">
                    <input type="text" name="phone_number" required placeholder="Số điện thoại (phone_number)">
                    <input type="text" name="city" required placeholder="Tỉnh / Thành phố (city)">
                    <input type="text" name="district" required placeholder="Quận / Huyện (district)">
                    <input type="text" name="ward" required placeholder="Phường / Xã (ward)">
                    <input type="text" name="street" placeholder="Đường / Phố (street)">
                    <textarea name="detail" rows="2" placeholder="Chi tiết thêm (detail)"></textarea>
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