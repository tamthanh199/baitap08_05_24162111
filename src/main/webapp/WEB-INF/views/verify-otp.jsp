<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Xác nhận OTP</title>

</head>

<body>

    <h1>Xác nhận OTP đăng ký</h1>

    <p>
        OTP đã được gửi tới:
        <strong>${email}</strong>
    </p>

    <p style="color: green;">
        ${successMessage}
    </p>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/verify-otp"
        method="post">

        <p>

            <label>Mã OTP:</label>

            <br>

            <input
                type="text"
                name="otp"
                maxlength="6"
                required>

        </p>

        <button type="submit">
            Kích hoạt tài khoản
        </button>

    </form>

    <form
        action="${pageContext.request.contextPath}/verify-otp"
        method="post"
        style="margin-top: 10px;">

        <input
            type="hidden"
            name="action"
            value="resend">

        <button type="submit">
            Gửi lại OTP
        </button>

    </form>

</body>

</html>