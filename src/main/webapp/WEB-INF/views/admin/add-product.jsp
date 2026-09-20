<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
    uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Thêm Product</title>

</head>

<body>

    <h1>Thêm Product</h1>

    <p style="color: red;">
        ${message}
    </p>

    <form
        action="${pageContext.request.contextPath}/admin/product/add"
        method="post"
        enctype="multipart/form-data">

        <p>

            <label>Tên sản phẩm:</label>

            <br>

            <input
                type="text"
                name="name"
                value="${name}"
                required>

        </p>

        <p>

            <label>Mô tả:</label>

            <br>

            <textarea
                name="description"
                rows="5"
                cols="50">${description}</textarea>

        </p>

        <p>

            <label>Giá:</label>

            <br>

            <input
                type="number"
                name="price"
                value="${price}"
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

                <option value="">
                    -- Chọn Category --
                </option>

                <c:forEach
                    items="${categories}"
                    var="category">

                    <option
                        value="${category.id}"
                        ${selectedCategoryId == category.id ? 'selected' : ''}>

                        ${category.name}

                    </option>

                </c:forEach>

            </select>

        </p>

        <p>

            <label>Ảnh sản phẩm:</label>

            <br>

            <input
                type="file"
                name="image"
                accept=".jpg,.jpeg,.png,.gif,.webp">

        </p>

        <button type="submit">
            Thêm
        </button>

        <a href="${pageContext.request.contextPath}/admin/product/list">
            Quay lại
        </a>

    </form>

</body>

</html>