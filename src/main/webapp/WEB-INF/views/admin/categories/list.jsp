<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Category</title>
    <style>.category-image{width:110px;height:75px;object-fit:cover;border-radius:8px;border:1px solid #ddd}</style>
</head>
<body>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="h3 mb-0">Quản lý Category</h1>
    <a class="btn btn-primary" href="<c:url value='/admin/categories/add'/>">+ Thêm Category</a>
</div>
<c:if test="${not empty message}"><div class="alert alert-info">${message}</div></c:if>
<form class="row g-2 mb-3" action="<c:url value='/admin/categories/searchpaginated'/>" method="get">
    <div class="col-md-7"><input class="form-control" name="keyword" value="${keyword}" placeholder="Nhập tên Category cần tìm..."></div>
    <div class="col-md-3">
        <select class="form-select" name="size">
            <option value="3" ${categoryPage.size == 3 ? 'selected' : ''}>3 / trang</option>
            <option value="5" ${categoryPage.size == 5 ? 'selected' : ''}>5 / trang</option>
            <option value="10" ${categoryPage.size == 10 ? 'selected' : ''}>10 / trang</option>
            <option value="15" ${categoryPage.size == 15 ? 'selected' : ''}>15 / trang</option>
            <option value="20" ${categoryPage.size == 20 ? 'selected' : ''}>20 / trang</option>
        </select>
    </div>
    <div class="col-md-2 d-grid"><button class="btn btn-outline-primary">Tìm kiếm</button></div>
</form>
<c:choose>
<c:when test="${!categoryPage.hasContent()}"><div class="alert alert-warning">Không có Category.</div></c:when>
<c:otherwise>
<div class="table-responsive">
<table class="table table-striped table-hover align-middle">
<thead class="table-dark"><tr><th>STT</th><th>ID</th><th>Ảnh</th><th>Tên Category</th><th>Status</th><th>Action</th></tr></thead>
<tbody>
<c:forEach items="${categoryPage.content}" var="cate" varStatus="stt">
<tr>
    <td>${categoryPage.number * categoryPage.size + stt.index + 1}</td>
    <td>${cate.categoryId}</td>
    <td><c:choose><c:when test="${not empty cate.images}"><img class="category-image" src="${pageContext.request.contextPath}/image?fname=${cate.images}" alt="${cate.categoryname}"></c:when><c:otherwise>Chưa có ảnh</c:otherwise></c:choose></td>
    <td>${cate.categoryname}</td>
    <td><c:choose><c:when test="${cate.status == 1}"><span class="badge text-bg-success">Active</span></c:when><c:otherwise><span class="badge text-bg-secondary">Inactive</span></c:otherwise></c:choose></td>
    <td class="text-nowrap">
        <a class="btn btn-sm btn-info" href="<c:url value='/admin/categories/view/${cate.categoryId}'/>">View</a>
        <a class="btn btn-sm btn-warning" href="<c:url value='/admin/categories/edit/${cate.categoryId}'/>">Edit</a>
        <a class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc muốn xóa Category này?')" href="<c:url value='/admin/categories/delete/${cate.categoryId}'/>">Delete</a>
    </td>
</tr>
</c:forEach>
</tbody></table></div>
</c:otherwise>
</c:choose>
<c:if test="${categoryPage.totalPages > 1}">
<nav><ul class="pagination justify-content-center">
<c:forEach items="${pageNumbers}" var="pageNumber">
<li class="page-item ${pageNumber == categoryPage.number + 1 ? 'active' : ''}"><a class="page-link" href="<c:url value='/admin/categories/searchpaginated?keyword=${keyword}&size=${categoryPage.size}&page=${pageNumber}'/>">${pageNumber}</a></li>
</c:forEach>
</ul></nav>
</c:if>
</body></html>
