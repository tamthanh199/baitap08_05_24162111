<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Chi tiết User</title>

</head>

<body>

<h1 class="h3">
    Chi tiết User
</h1>

<dl class="row mt-3">

    <dt class="col-sm-3">
        User ID
    </dt>

    <dd class="col-sm-9">
        ${user.userId}
    </dd>

    <dt class="col-sm-3">
        Username
    </dt>

    <dd class="col-sm-9">
        ${user.username}
    </dd>

    <dt class="col-sm-3">
        Họ tên
    </dt>

    <dd class="col-sm-9">
        ${user.fullName}
    </dd>

    <dt class="col-sm-3">
        Email
    </dt>

    <dd class="col-sm-9">
        ${user.email}
    </dd>

    <dt class="col-sm-3">
        Phone
    </dt>

    <dd class="col-sm-9">
        ${user.phone}
    </dd>

    <dt class="col-sm-3">
        Images
    </dt>

    <dd class="col-sm-9">
        ${user.images}
    </dd>

    <dt class="col-sm-3">
        Role
    </dt>

    <dd class="col-sm-9">

        <c:choose>

            <c:when test="${user.role == 'ADMIN'}">

                <span class="badge bg-danger">
                    ADMIN
                </span>

            </c:when>

            <c:otherwise>

                <span class="badge bg-secondary">
                    USER
                </span>

            </c:otherwise>

        </c:choose>

    </dd>

</dl>

<a
    class="btn btn-warning"
    href="<c:url value='/admin/users/edit/${user.userId}'/>">

    Edit

</a>

<a
    class="btn btn-secondary"
    href="<c:url value='/admin/users'/>">

    Back

</a>

</body>

</html>