<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Thông tin cá nhân</title>

</head>

<body>

    <h1>Thông tin cá nhân</h1>

    <c:if test="${param.success == 'true'}">

        <p style="color: green;">
            Cập nhật thông tin thành công.
        </p>

    </c:if>

    <c:if test="${not empty message}">

        <p style="color: red;">
            ${message}
        </p>

    </c:if>

    <c:if test="${not empty user.images}">

        <p>
            <strong>Ảnh đại diện hiện tại:</strong>
        </p>

        <img
            src="${pageContext.request.contextPath}/profile-image?fname=${user.images}"
            alt="Ảnh đại diện"
            width="150"
            height="150"
            style="
                object-fit: cover;
                border-radius: 8px;
                border: 1px solid #cccccc;
            ">

        <br><br>

    </c:if>

    <form
        action="${pageContext.request.contextPath}/profile"
        method="post"
        enctype="multipart/form-data">

        <div>

            <label>
                Tài khoản:
            </label>

            <br>

            <input
                type="text"
                value="${user.username}"
                readonly>

        </div>

        <br>

        <div>

            <label>
                Email:
            </label>

            <br>

            <input
                type="email"
                value="${user.email}"
                readonly>

        </div>

        <br>

        <div>

            <label>
                Họ và tên:
            </label>

            <br>

            <input
                type="text"
                name="fullName"
                value="${user.fullName}"
                required>

        </div>

        <br>

        <div>

            <label>
                Số điện thoại:
            </label>

            <br>

            <input
                type="text"
                name="phone"
                value="${user.phone}">

        </div>

        <br>

        <div>

            <label>
                Ảnh đại diện:
            </label>

            <br>

            <input
                type="file"
                name="image"
                accept="image/jpeg,image/png,image/gif,image/webp">

        </div>

        <br>

        <button type="submit">
            Cập nhật thông tin
        </button>

    </form>

</body>

</html>