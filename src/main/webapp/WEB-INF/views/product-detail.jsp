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

    <title>
        ${product.name}
    </title>

    <style>

        .detail {
            display: flex;
            gap: 30px;
        }

        .detail-image {
            width: 360px;
            height: 280px;
            object-fit: cover;
            border: 1px solid #ddd;
        }

        .description {
            white-space: pre-wrap;
        }

    </style>

</head>

<body>

    <h1>Chi tiết sản phẩm</h1>

    <div class="detail">

        <div>

            <c:choose>

                <c:when test="${not empty product.image}">

                    <img
                        class="detail-image"
                        src="${pageContext.request.contextPath}/product-image?fname=${product.image}"
                        alt="${product.name}">

                </c:when>

                <c:otherwise>

                    <div
                        class="detail-image"
                        style="
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            background: #eee;
                        ">

                        Chưa có ảnh

                    </div>

                </c:otherwise>

            </c:choose>

        </div>

        <div>

            <h2>
                ${product.name}
            </h2>

            <p>

                Danh mục:

                <strong>
                    ${product.category.name}
                </strong>

            </p>

            <p>

                Giá:

                <strong>

                    <fmt:formatNumber
                        value="${product.price}"
                        type="number"/>

                    đ

                </strong>

            </p>

            <p class="description">
                ${product.description}
            </p>

        </div>

    </div>

    <p>

        <a href="${pageContext.request.contextPath}/product">
            Quay lại danh sách sản phẩm
        </a>

    </p>

</body>

</html>