<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Đặt lại mật khẩu</title>

</head>

<body>

    <h1>Đặt lại mật khẩu</h1>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/reset-password"
        method="post">

        <p>

            <label>Mật khẩu mới:</label>

            <br>

            <input
                type="password"
                name="password"
                required>

        </p>

        <p>

            <label>Xác nhận mật khẩu mới:</label>

            <br>

            <input
                type="password"
                name="confirmPassword"
                required>

        </p>

        <button type="submit">
            Đổi mật khẩu
        </button>

    </form>

</body>

</html>