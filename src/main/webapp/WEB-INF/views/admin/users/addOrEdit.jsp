<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <title>${user.isEdit ? 'Edit User' : 'Add User'}</title>
</head>

<body>

<h1 class="h3 mb-3">
    ${user.isEdit ? 'Edit User' : 'Add New User'}
</h1>

<c:url var="saveUrl" value="/admin/users/saveOrUpdate"/>

<form:form
    action="${saveUrl}"
    method="post"
    modelAttribute="user">

    <form:hidden path="isEdit"/>
    <form:hidden path="userId"/>

    <c:if test="${user.isEdit}">
        <div class="mb-3">
            <label class="form-label">User ID</label>
            <input
                class="form-control"
                value="${user.userId}"
                readonly>
        </div>
    </c:if>

    <div class="mb-3">
        <label class="form-label">Username</label>

        <form:input
            path="username"
            cssClass="form-control"/>

        <form:errors
            path="username"
            cssClass="text-danger"/>
    </div>

    <div class="mb-3">
        <label class="form-label">Password</label>

        <form:password
            path="password"
            cssClass="form-control"/>

        <div class="form-text">
            ${user.isEdit ? 'Để trống nếu không đổi mật khẩu.' : 'Bắt buộc khi thêm mới.'}
        </div>

        <form:errors
            path="password"
            cssClass="text-danger"/>
    </div>

    <div class="mb-3">
        <label class="form-label">Họ tên</label>

        <form:input
            path="fullName"
            cssClass="form-control"/>

        <form:errors
            path="fullName"
            cssClass="text-danger"/>
    </div>

    <div class="mb-3">
        <label class="form-label">Email</label>

        <form:input
            path="email"
            type="email"
            cssClass="form-control"/>

        <form:errors
            path="email"
            cssClass="text-danger"/>
    </div>

    <div class="mb-3">
        <label class="form-label">Phone</label>

        <form:input
            path="phone"
            cssClass="form-control"/>

        <form:errors
            path="phone"
            cssClass="text-danger"/>
    </div>

    <div class="mb-3">
        <label class="form-label">Role</label>

        <form:select
            path="role"
            cssClass="form-select">

            <form:option value="USER">
                USER
            </form:option>

            <form:option value="ADMIN">
                ADMIN
            </form:option>

        </form:select>

        <form:errors
            path="role"
            cssClass="text-danger"/>
    </div>

    <button
        class="btn btn-primary"
        type="submit">

        ${user.isEdit ? 'Update' : 'Save'}

    </button>

    <a
        class="btn btn-secondary"
        href="<c:url value='/admin/users'/>">

        List Users

    </a>

</form:form>

</body>
</html>
