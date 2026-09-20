<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Đăng nhập</title>

</head>

<body>

    <h1>Đăng nhập</h1>

    <p style="color: green;">
        ${successMessage}
    </p>

    <c:if test="${param.reset == 'true'}">

        <p style="color: green;">
            Đổi mật khẩu thành công.
            Vui lòng đăng nhập.
        </p>

    </c:if>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/login"
        method="post">

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

            <label>

                <input
                    type="checkbox"
                    name="remember">

                Nhớ đăng nhập

            </label>

        </p>

        <button type="submit">
            Đăng nhập
        </button>

        &nbsp;

        <a href="${pageContext.request.contextPath}/register">
            Đăng ký
        </a>

        &nbsp;

        <a href="${pageContext.request.contextPath}/forgot-password">
            Quên mật khẩu?
        </a>

    </form>

</body>

</html>