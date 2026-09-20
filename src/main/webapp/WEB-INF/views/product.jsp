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

    <title>Sản phẩm</title>

    <style>

        .product-grid {
            display: grid;
            grid-template-columns:
                repeat(3, 1fr);
            gap: 20px;
        }

        .product-card {
            border: 1px solid #ddd;
            padding: 12px;
            border-radius: 8px;
        }

        .product-card img {
            width: 100%;
            height: 180px;
            object-fit: cover;
        }

        .pagination {
            margin-top: 25px;
        }

        .pagination a,
        .pagination strong {
            margin-right: 8px;
        }

    </style>

</head>

<body>

    <h1>Tất cả sản phẩm</h1>

    <p>
        Tổng số sản phẩm:
        ${totalItems}
    </p>

    <c:choose>

        <c:when test="${empty products}">

            <p>
                Chưa có sản phẩm.
            </p>

        </c:when>

        <c:otherwise>

            <div class="product-grid">

                <c:forEach
                    items="${products}"
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
                                        height: 180px;
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
                            Danh mục:
                            ${product.category.name}
                        </p>

                        <p>

                            <strong>

                                <fmt:formatNumber
                                    value="${product.price}"
                                    type="number"/>

                                đ

                            </strong>

                        </p>

                    </div>

                </c:forEach>

            </div>

            <c:if test="${totalPages > 1}">

                <div class="pagination">

                    <c:if test="${currentPage > 1}">

                        <a href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">
                            Trước
                        </a>

                    </c:if>

                    <c:forEach
                        begin="1"
                        end="${totalPages}"
                        var="pageNumber">

                        <c:choose>

                            <c:when test="${pageNumber == currentPage}">

                                <strong>
                                    ${pageNumber}
                                </strong>

                            </c:when>

                            <c:otherwise>

                                <a href="${pageContext.request.contextPath}/product?page=${pageNumber}">
                                    ${pageNumber}
                                </a>

                            </c:otherwise>

                        </c:choose>

                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">

                        <a href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">
                            Sau
                        </a>

                    </c:if>

                </div>

            </c:if>

        </c:otherwise>

    </c:choose>

</body>

</html>