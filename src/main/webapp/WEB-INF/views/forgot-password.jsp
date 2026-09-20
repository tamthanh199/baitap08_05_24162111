<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Quên mật khẩu</title>

</head>

<body>

    <h1>Quên mật khẩu</h1>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/forgot-password"
        method="post">

        <p>

            <label>Email đã đăng ký:</label>

            <br>

            <input
                type="email"
                name="email"
                value="${email}"
                required>

        </p>

        <button type="submit">
            Gửi OTP
        </button>

        <a href="${pageContext.request.contextPath}/login">
            Quay lại đăng nhập
        </a>

    </form>

</body>

</html>