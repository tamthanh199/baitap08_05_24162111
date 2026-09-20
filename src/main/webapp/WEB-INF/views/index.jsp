<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core" %>

<%@ taglib prefix="fmt"
    uri="jakarta.tags.fmt" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Spring Boot 4 CRUD MVC</title>

    <style>

        .product-grid {
            display: grid;
            grid-template-columns:
                repeat(5, 1fr);
            gap: 15px;
        }

        .product-card {
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 8px;
        }

        .product-card img {
            width: 100%;
            height: 120px;
            object-fit: cover;
        }

    </style>

</head>

<body>

    <h1>Spring Boot 4 CRUD MVC</h1>

    <c:choose>

        <c:when test="${not empty sessionScope.account}">

            <h2>
                Đăng nhập Session thành công
            </h2>

            <p>

                Xin chào

                <strong>
                    ${sessionScope.account.fullName}
                </strong>

            </p>

            <p>
                Tài khoản:
                ${sessionScope.account.username}
            </p>

        </c:when>

        <c:otherwise>

            <p>
                Bạn có thể xem sản phẩm
                mà không cần đăng nhập.
            </p>

        </c:otherwise>

    </c:choose>

    <hr>

    <h2>
        10 sản phẩm mới nhất
    </h2>

    <c:choose>

        <c:when test="${empty latestProducts}">

            <p>
                Chưa có sản phẩm.
            </p>

        </c:when>

        <c:otherwise>

            <div class="product-grid">

                <c:forEach
                    items="${latestProducts}"
                    var="product">

                    <div class="product-card">

                        <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}">

                            <c:choose>

                                <c:when test="${not empty product.image}">

                                    <img
                                        src="${pageContext.request.contextPath}/product-image?fname=${product.image}"
                                        alt="${product.name}">

                                </c:when>

                                <c:otherwise>

                                    <div style="
                                        height: 120px;
                                        background: #eee;
                                        display: flex;
                                        align-items: center;
                                        justify-content: center;
                                    ">

                                        Chưa có ảnh

                                    </div>

                                </c:otherwise>

                            </c:choose>

                        </a>

                        <h3>

                            <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}">
                                ${product.name}
                            </a>

                        </h3>

                        <p>

                            <fmt:formatNumber
                                value="${product.price}"
                                type="number"/>

                            đ

                        </p>

                    </div>

                </c:forEach>

            </div>

        </c:otherwise>

    </c:choose>

    <p style="margin-top: 20px;">

        <a href="${pageContext.request.contextPath}/product">
            Xem tất cả sản phẩm
        </a>

    </p>

</body>

</html>