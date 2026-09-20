<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Chi tiết Category</title></head><body>
<h1 class="h3">Chi tiết Category</h1><dl class="row mt-3">
<dt class="col-sm-3">Category ID</dt><dd class="col-sm-9">${category.categoryId}</dd>
<dt class="col-sm-3">Category Name</dt><dd class="col-sm-9">${category.categoryname}</dd>
<dt class="col-sm-3">Images</dt><dd class="col-sm-9">${category.images}</dd>
<dt class="col-sm-3">Status</dt><dd class="col-sm-9">${category.status}</dd>
</dl>
<a class="btn btn-warning" href="<c:url value='/admin/categories/edit/${category.categoryId}'/>">Edit</a>
<a class="btn btn-secondary" href="<c:url value='/admin/categories'/>">Back</a>
</body></html>
