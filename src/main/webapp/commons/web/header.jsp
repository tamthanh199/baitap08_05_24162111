<%@ page
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib
    prefix="c"
    uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">

    <div class="container">

        <a
            class="navbar-brand fw-semibold"
            href="${pageContext.request.contextPath}/home">

            WEBPR330479

        </a>

        <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#mainNavbar"
            aria-controls="mainNavbar"
            aria-expanded="false"
            aria-label="Toggle navigation">

            <span class="navbar-toggler-icon"></span>

        </button>

        <div
            class="collapse navbar-collapse"
            id="mainNavbar">

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">

                    <a
                        class="nav-link"
                        href="${pageContext.request.contextPath}/home">

                        Trang chủ

                    </a>

                </li>

                <li class="nav-item">

                    <a
                        class="nav-link"
                        href="${pageContext.request.contextPath}/product">

                        Sản phẩm

                    </a>

                </li>

                <c:if test="${not empty sessionScope.account
                    and sessionScope.account.role == 'ADMIN'}">

                    <li class="nav-item">

                        <a
                            class="nav-link"
                            href="${pageContext.request.contextPath}/admin/categories">

                            Category CRUD

                        </a>

                    </li>

                    <li class="nav-item">

                        <a
                            class="nav-link"
                            href="${pageContext.request.contextPath}/admin/ajax/categories">

                            Category AJAX

                        </a>

                    </li>

                    <li class="nav-item">

                        <a
                            class="nav-link"
                            href="${pageContext.request.contextPath}/admin/users">

                            User CRUD

                        </a>

                    </li>

                    <li class="nav-item">

                        <a
                            class="nav-link"
                            href="${pageContext.request.contextPath}/admin/product/list">

                            Product CRUD

                        </a>

                    </li>

                    <li class="nav-item">

                        <a
                            class="nav-link"
                            href="${pageContext.request.contextPath}/admin/ajax/products">

                            Product AJAX

                        </a>

                    </li>

                </c:if>

            </ul>

            <div class="d-flex align-items-center gap-2">

                <c:choose>

                    <c:when test="${not empty sessionScope.account}">

                        <span class="navbar-text text-light me-2">

                            Xin chào,

                            <strong>
                                ${sessionScope.account.fullName}
                            </strong>

                            <c:if test="${sessionScope.account.role == 'ADMIN'}">

                                <span class="badge bg-danger ms-1">
                                    ADMIN
                                </span>

                            </c:if>

                        </span>

                        <a
                            class="btn btn-outline-light btn-sm"
                            href="${pageContext.request.contextPath}/profile">

                            Profile

                        </a>

                        <a
                            class="btn btn-danger btn-sm"
                            href="${pageContext.request.contextPath}/logout">

                            Đăng xuất

                        </a>

                    </c:when>

                    <c:otherwise>

                        <a
                            class="btn btn-outline-light btn-sm"
                            href="${pageContext.request.contextPath}/login">

                            Đăng nhập

                        </a>

                        <a
                            class="btn btn-primary btn-sm"
                            href="${pageContext.request.contextPath}/register">

                            Đăng ký

                        </a>

                    </c:otherwise>

                </c:choose>

            </div>

        </div>

    </div>

</nav>