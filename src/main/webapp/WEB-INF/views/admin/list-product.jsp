<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
    uri="jakarta.tags.core" %>

<%@ taglib prefix="fmt"
    uri="jakarta.tags.fmt" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Quản lý Product</title>

    <style>

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th,
        td {
            border: 1px solid #999;
            padding: 8px;
            text-align: center;
        }

        th {
            background: #eee;
        }

        .product-image {
            width: 100px;
            height: 75px;
            object-fit: cover;
        }

    </style>

</head>

<body>

    <h1>Danh sách Product</h1>

    <p>

        <a href="${pageContext.request.contextPath}/admin/product/add">
            Thêm sản phẩm
        </a>

    </p>

    <table>

        <thead>

            <tr>

                <th>STT</th>
                <th>ID</th>
                <th>Tên</th>
                <th>Giá</th>
                <th>Category</th>
                <th>Ảnh</th>
                <th>Thao tác</th>

            </tr>

        </thead>

        <tbody>

            <c:forEach
                items="${products}"
                var="product"
                varStatus="status">

                <tr>

                    <td>
                        ${status.index + 1}
                    </td>

                    <td>
                        ${product.id}
                    </td>

                    <td>
                        ${product.name}
                    </td>

                    <td>

                        <fmt:formatNumber
                            value="${product.price}"
                            type="number"/>

                        đ

                    </td>

                    <td>
                        ${product.category.name}
                    </td>

                    <td>

                        <c:choose>

                            <c:when test="${not empty product.image}">

                                <img
                                    class="product-image"
                                    src="${pageContext.request.contextPath}/product-image?fname=${product.image}"
                                    alt="${product.name}">

                            </c:when>

                            <c:otherwise>

                                Chưa có ảnh

                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/product/edit?id=${product.id}">
                            Sửa
                        </a>

                        <a
                            href="${pageContext.request.contextPath}/admin/product/delete?id=${product.id}"
                            onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">

                            Xóa

                        </a>

                    </td>

                </tr>

            </c:forEach>

        </tbody>

    </table>

</body>

</html>