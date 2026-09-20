<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Đăng ký tài khoản</title>

</head>

<body>

    <h1>Đăng ký tài khoản</h1>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/register"
        method="post">

        <p>

            <label>Họ tên:</label>

            <br>

            <input
                type="text"
                name="fullName"
                value="${fullName}"
                required>

        </p>

        <p>

            <label>Email:</label>

            <br>

            <input
                type="email"
                name="email"
                value="${email}"
                required>

        </p>

        <p>

            <label>Số điện thoại:</label>

            <br>

            <input
                type="text"
                name="phone"
                value="${phone}"
                required>

        </p>

        <p>

            <label>Tài khoản:</label>

            <br>

            <input
                type="text"
                name="username"
                value="${username}"
                required>

        </p>

        <p>

            <label>Mật khẩu:</label>

            <br>

            <input
                type="password"
                name="password"
                required>

        </p>

        <p>

            <label>Xác nhận mật khẩu:</label>

            <br>

            <input
                type="password"
                name="confirmPassword"
                required>

        </p>

        <button type="submit">
            Đăng ký
        </button>

        &nbsp;

        <a href="${pageContext.request.contextPath}/login">
            Đăng nhập
        </a>

    </form>

</body>

</html>