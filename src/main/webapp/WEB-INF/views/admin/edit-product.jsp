<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
    uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Sửa Product</title>

    <style>

        .current-image {
            width: 180px;
            height: 130px;
            object-fit: cover;
            border: 1px solid #ccc;
        }

    </style>

</head>

<body>

    <h1>Sửa Product</h1>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/admin/product/edit"
        method="post"
        enctype="multipart/form-data">

        <input
            type="hidden"
            name="id"
            value="${product.id}">

        <p>

            <label>Tên sản phẩm:</label>

            <br>

            <input
                type="text"
                name="name"
                value="${product.name}"
                required>

        </p>

        <p>

            <label>Mô tả:</label>

            <br>

            <textarea
                name="description"
                rows="5"
                cols="50">${product.description}</textarea>

        </p>

        <p>

            <label>Giá:</label>

            <br>

            <input
                type="number"
                name="price"
                value="${product.price}"
                min="0"
                step="0.01"
                required>

        </p>

        <p>

            <label>Danh mục:</label>

            <br>

            <select
                name="categoryId"
                required>

                <c:forEach
                    items="${categories}"
                    var="category">

                    <option
                        value="${category.id}"
                        ${(not empty selectedCategoryId
                            && selectedCategoryId == category.id)
                            ||
                            (empty selectedCategoryId
                            && product.category.id == category.id)
                            ? 'selected' : ''}>

                        ${category.name}

                    </option>

                </c:forEach>

            </select>

        </p>

        <p>

            <label>Ảnh hiện tại:</label>

            <br>

            <c:choose>

                <c:when test="${not empty product.image}">

                    <img
                        class="current-image"
                        src="${pageContext.request.contextPath}/product-image?fname=${product.image}"
                        alt="${product.name}">

                </c:when>

                <c:otherwise>

                    Chưa có ảnh

                </c:otherwise>

            </c:choose>

        </p>

        <p>

            <label>Chọn ảnh mới:</label>

            <br>

            <input
                type="file"
                name="image"
                accept=".jpg,.jpeg,.png,.gif,.webp">

        </p>

        <button type="submit">
            Lưu thay đổi
        </button>

        <a href="${pageContext.request.contextPath}/admin/product/list">
            Quay lại
        </a>

    </form>

</body>

</html>