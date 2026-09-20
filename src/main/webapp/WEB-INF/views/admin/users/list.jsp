<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <title>Quản lý User</title>

</head>

<body>

<div class="d-flex justify-content-between align-items-center mb-3">

    <h1 class="h3 mb-0">
        Quản lý User
    </h1>

    <a
        class="btn btn-primary"
        href="<c:url value='/admin/users/add'/>">

        + Thêm User

    </a>

</div>

<c:if test="${not empty message}">

    <div class="alert alert-info">
        ${message}
    </div>

</c:if>

<form
    class="row g-2 mb-3"
    action="<c:url value='/admin/users/searchpaginated'/>"
    method="get">

    <div class="col-md-7">

        <input
            class="form-control"
            name="keyword"
            value="${keyword}"
            placeholder="Tìm username, họ tên hoặc email...">

    </div>

    <div class="col-md-3">

        <select
            class="form-select"
            name="size">

            <option
                value="3"
                ${userPage.size == 3 ? 'selected' : ''}>
                3 / trang
            </option>

            <option
                value="5"
                ${userPage.size == 5 ? 'selected' : ''}>
                5 / trang
            </option>

            <option
                value="10"
                ${userPage.size == 10 ? 'selected' : ''}>
                10 / trang
            </option>

            <option
                value="15"
                ${userPage.size == 15 ? 'selected' : ''}>
                15 / trang
            </option>

            <option
                value="20"
                ${userPage.size == 20 ? 'selected' : ''}>
                20 / trang
            </option>

        </select>

    </div>

    <div class="col-md-2 d-grid">

        <button
            class="btn btn-outline-primary"
            type="submit">

            Tìm kiếm

        </button>

    </div>

</form>

<c:choose>

    <c:when test="${!userPage.hasContent()}">

        <div class="alert alert-warning">
            Không có User.
        </div>

    </c:when>

    <c:otherwise>

        <div class="table-responsive">

            <table class="table table-striped table-hover align-middle">

                <thead class="table-dark">

                <tr>

                    <th>STT</th>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Action</th>

                </tr>

                </thead>

                <tbody>

                <c:forEach
                    items="${userPage.content}"
                    var="u"
                    varStatus="stt">

                    <tr>

                        <td>

                            ${userPage.number
                              * userPage.size
                              + stt.index + 1}

                        </td>

                        <td>
                            ${u.userId}
                        </td>

                        <td>
                            ${u.username}
                        </td>

                        <td>
                            ${u.fullName}
                        </td>

                        <td>
                            ${u.email}
                        </td>

                        <td>
                            ${u.phone}
                        </td>

                        <td>

                            <c:choose>

                                <c:when test="${u.role == 'ADMIN'}">

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

                        </td>

                        <td class="text-nowrap">

                            <a
                                class="btn btn-sm btn-info"
                                href="<c:url value='/admin/users/view/${u.userId}'/>">

                                View

                            </a>

                            <a
                                class="btn btn-sm btn-warning"
                                href="<c:url value='/admin/users/edit/${u.userId}'/>">

                                Edit

                            </a>

                            <a
                                class="btn btn-sm btn-danger"
                                href="<c:url value='/admin/users/delete/${u.userId}'/>"
                                onclick="return confirm('Bạn có chắc muốn xóa User này?')">

                                Delete

                            </a>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </div>

    </c:otherwise>

</c:choose>

<c:if test="${userPage.totalPages > 1}">

    <nav>

        <ul class="pagination justify-content-center">

            <c:forEach
                items="${pageNumbers}"
                var="pageNumber">

                <li
                    class="page-item ${pageNumber == userPage.number + 1 ? 'active' : ''}">

                    <a
                        class="page-link"
                        href="<c:url value='/admin/users/searchpaginated?keyword=${keyword}&size=${userPage.size}&page=${pageNumber}'/>">

                        ${pageNumber}

                    </a>

                </li>

            </c:forEach>

        </ul>

    </nav>

</c:if>

</body>

</html>