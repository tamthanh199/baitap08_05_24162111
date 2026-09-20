<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>
        ${category.isEdit ? 'Sửa Category' : 'Thêm Category'}
    </title>

</head>

<body>

<h1 class="h3 mb-3">
    ${category.isEdit ? 'Sửa Category' : 'Thêm Category'}
</h1>

<c:if test="${not empty message}">

    <div class="alert alert-danger">
        ${message}
    </div>

</c:if>

<c:url
    var="saveUrl"
    value="/admin/categories/saveOrUpdate"/>

<form:form
    action="${saveUrl}"
    method="post"
    modelAttribute="category"
    enctype="multipart/form-data">

    <form:hidden path="isEdit"/>

    <form:hidden path="categoryId"/>

    <form:hidden path="images"/>

    <c:if test="${category.isEdit}">

        <div class="mb-3">

            <label class="form-label">
                Category ID
            </label>

            <input
                class="form-control"
                value="${category.categoryId}"
                readonly>

        </div>

    </c:if>

    <div class="mb-3">

        <label class="form-label">
            Tên Category
        </label>

        <form:input
            path="categoryname"
            cssClass="form-control"
            maxlength="255"/>

        <form:errors
            path="categoryname"
            cssClass="text-danger"/>

    </div>

    <c:if test="${not empty category.images}">

        <div class="mb-3">

            <label class="form-label d-block">
                Ảnh hiện tại
            </label>

            <img
                src="${pageContext.request.contextPath}/image?fname=${category.images}"
                style="width:160px;height:100px;object-fit:cover;border-radius:8px">

        </div>

    </c:if>

    <div class="mb-3">

        <label class="form-label">
            Ảnh Category
        </label>

        <input
            class="form-control"
            type="file"
            name="imageFile"
            accept=".jpg,.jpeg,.png,.gif,.webp">

    </div>

    <div class="mb-3">

        <label class="form-label">
            Status
        </label>

        <form:select
            path="status"
            cssClass="form-select">

            <form:option value="1">
                Active
            </form:option>

            <form:option value="0">
                Inactive
            </form:option>

        </form:select>

    </div>

    <button
        class="btn btn-primary"
        type="submit">

        ${category.isEdit ? 'Cập nhật' : 'Thêm'}

    </button>

    <a
        class="btn btn-secondary"
        href="<c:url value='/admin/categories'/>">

        Quay lại

    </a>

</form:form>

</body>

</html>