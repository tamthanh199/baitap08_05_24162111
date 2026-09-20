<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">

    <title>Category CRUD AJAX</title>

    <style>
        .category-img {
            width: 90px;
            height: 65px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        .preview-img {
            width: 150px;
            height: 110px;
            object-fit: cover;
            border-radius: 10px;
            border: 1px solid #ddd;
        }
    </style>
</head>

<body>

<div class="d-flex justify-content-between align-items-center mb-4">

    <div>
        <h2 class="mb-1">
            Category CRUD bằng AJAX
        </h2>

        <p class="text-muted mb-0">
            Dữ liệu được tải và cập nhật thông qua REST API
        </p>
    </div>

    <button
        type="button"
        class="btn btn-primary"
        onclick="openCreateCategoryModal()">

        + Thêm Category
    </button>

</div>


<div
    id="messageBox"
    class="alert d-none"
    role="alert">
</div>


<div class="table-responsive">

    <table
        class="table table-striped table-hover align-middle">

        <thead class="table-dark">

        <tr>
            <th>STT</th>
            <th>ID</th>
            <th>Ảnh</th>
            <th>Tên Category</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        </thead>

        <tbody id="categoryTableBody">

        <tr>
            <td
                colspan="6"
                class="text-center">

                Đang tải dữ liệu...

            </td>
        </tr>

        </tbody>

    </table>

</div>


<!-- MODAL THÊM / SỬA CATEGORY -->

<div
    class="modal fade"
    id="categoryModal"
    tabindex="-1"
    aria-hidden="true">

    <div class="modal-dialog">

        <div class="modal-content">

            <form
                id="categoryForm"
                enctype="multipart/form-data">

                <div class="modal-header">

                    <h5
                        class="modal-title"
                        id="categoryModalTitle">

                        Thêm Category

                    </h5>

                    <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal">
                    </button>

                </div>


                <div class="modal-body">

                    <input
                        type="hidden"
                        id="categoryId"
                        name="categoryId">


                    <div class="mb-3">

                        <label
                            for="categoryName"
                            class="form-label">

                            Tên Category

                        </label>

                        <input
                            type="text"
                            class="form-control"
                            id="categoryName"
                            name="categoryName"
                            required>

                    </div>


                    <div class="mb-3">

                        <label
                            for="status"
                            class="form-label">

                            Trạng thái

                        </label>

                        <select
                            class="form-select"
                            id="status"
                            name="status">

                            <option value="1">
                                Active
                            </option>

                            <option value="0">
                                Inactive
                            </option>

                        </select>

                    </div>


                    <div class="mb-3">

                        <label
                            for="icon"
                            class="form-label">

                            Ảnh Category

                        </label>

                        <input
                            type="file"
                            class="form-control"
                            id="icon"
                            name="icon"
                            accept=".jpg,.jpeg,.png,.gif,.webp">

                        <div class="form-text">

                            Khi sửa, nếu không chọn ảnh mới
                            thì hệ thống giữ ảnh hiện tại.

                        </div>

                    </div>


                    <div
                        id="imagePreviewArea"
                        class="d-none">

                        <p class="mb-2">
                            Ảnh hiện tại:
                        </p>

                        <img
                            id="imagePreview"
                            class="preview-img"
                            alt="Category">

                    </div>

                </div>


                <div class="modal-footer">

                    <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">

                        Đóng

                    </button>

                    <button
                        type="submit"
                        class="btn btn-primary"
                        id="saveCategoryButton">

                        Lưu

                    </button>

                </div>

            </form>

        </div>

    </div>

</div>


<script>

    let categoryModal;


    $(document).ready(function () {

        categoryModal =
            bootstrap.Modal.getOrCreateInstance(
                document.getElementById(
                    "categoryModal"
                )
            );


        loadCategories();


        $("#categoryForm").on(
            "submit",
            function (event) {

                event.preventDefault();

                saveCategory();
            }
        );


        $("#icon").on(
            "change",
            function () {

                const file =
                    this.files[0];


                if (!file) {
                    return;
                }


                const reader =
                    new FileReader();


                reader.onload =
                    function (event) {

                        $("#imagePreview")
                            .attr(
                                "src",
                                event.target.result
                            );


                        $("#imagePreviewArea")
                            .removeClass(
                                "d-none"
                            );
                    };


                reader.readAsDataURL(
                    file
                );
            }
        );
    });


    /* =====================================
       LOAD CATEGORY
       ===================================== */

    function loadCategories() {

        $("#categoryTableBody").html(
            "<tr>"
            + "<td colspan='6' class='text-center'>"
            + "Đang tải dữ liệu..."
            + "</td>"
            + "</tr>"
        );


        $.ajax({

            url:
                contextPath
                + "/api/category",

            type:
                "GET",

            dataType:
                "json",


            success:
                function (response) {

                    if (!response.status) {

                        showMessage(
                            response.message,
                            false
                        );

                        return;
                    }


                    renderCategories(
                        response.body
                    );
                },


            error:
                function (xhr) {

                    showMessage(
                        getErrorMessage(xhr),
                        false
                    );


                    $("#categoryTableBody").html(

                        "<tr>"
                        + "<td colspan='6' "
                        + "class='text-center text-danger'>"
                        + "Không thể tải dữ liệu."
                        + "</td>"
                        + "</tr>"
                    );
                }
        });
    }


    /* =====================================
       RENDER CATEGORY
       ===================================== */

    function renderCategories(
            categories) {

        if (!categories
                || categories.length === 0) {

            $("#categoryTableBody").html(

                "<tr>"
                + "<td colspan='6' "
                + "class='text-center'>"
                + "Chưa có Category."
                + "</td>"
                + "</tr>"
            );

            return;
        }


        let html = "";


        categories.forEach(
            function (
                category,
                index) {


                let imageHtml =
                    "<span class='text-muted'>"
                    + "Chưa có ảnh"
                    + "</span>";


                if (category.icon) {

                    let imageUrl =
                        contextPath
                        + "/image?fname="
                        + encodeURIComponent(
                            category.icon
                        );


                    imageHtml =
                        "<img "
                        + "src='"
                        + imageUrl
                        + "' "
                        + "class='category-img' "
                        + "alt='Category'>";
                }


                let statusHtml;


                if (Number(category.status) === 1) {

                    statusHtml =
                        "<span "
                        + "class='badge text-bg-success'>"
                        + "Active"
                        + "</span>";

                } else {

                    statusHtml =
                        "<span "
                        + "class='badge text-bg-secondary'>"
                        + "Inactive"
                        + "</span>";
                }


                html +=
                    "<tr>"

                    + "<td>"
                    + (index + 1)
                    + "</td>"

                    + "<td>"
                    + category.categoryId
                    + "</td>"

                    + "<td>"
                    + imageHtml
                    + "</td>"

                    + "<td>"
                    + escapeHtml(
                        category.categoryName
                    )
                    + "</td>"

                    + "<td>"
                    + statusHtml
                    + "</td>"

                    + "<td class='text-nowrap'>"

                    + "<button "
                    + "type='button' "
                    + "class='btn btn-sm btn-warning me-1' "
                    + "onclick='openEditCategoryModal("
                    + category.categoryId
                    + ")'>"
                    + "Sửa"
                    + "</button>"

                    + "<button "
                    + "type='button' "
                    + "class='btn btn-sm btn-danger' "
                    + "onclick='deleteCategory("
                    + category.categoryId
                    + ")'>"
                    + "Xóa"
                    + "</button>"

                    + "</td>"

                    + "</tr>";
            }
        );


        $("#categoryTableBody")
            .html(html);
    }


    /* =====================================
       OPEN CREATE MODAL
       ===================================== */

    function openCreateCategoryModal() {

        $("#categoryForm")[0]
            .reset();


        $("#categoryId")
            .val("");


        $("#categoryModalTitle")
            .text(
                "Thêm Category"
            );


        $("#saveCategoryButton")
            .text(
                "Thêm"
            );


        $("#imagePreview")
            .attr(
                "src",
                ""
            );


        $("#imagePreviewArea")
            .addClass(
                "d-none"
            );


        categoryModal.show();
    }


    /* =====================================
       OPEN EDIT MODAL
       ===================================== */

    function openEditCategoryModal(
            categoryId) {


        $.ajax({

            url:
                contextPath
                + "/api/category/getCategory",

            type:
                "POST",

            data: {
                id: categoryId
            },

            dataType:
                "json",


            success:
                function (response) {

                    if (!response.status) {

                        showMessage(
                            response.message,
                            false
                        );

                        return;
                    }


                    const category =
                        response.body;


                    $("#categoryForm")[0]
                        .reset();


                    $("#categoryId")
                        .val(
                            category.categoryId
                        );


                    $("#categoryName")
                        .val(
                            category.categoryName
                        );


                    $("#status")
                        .val(
                            category.status
                        );


                    $("#categoryModalTitle")
                        .text(
                            "Cập nhật Category"
                        );


                    $("#saveCategoryButton")
                        .text(
                            "Cập nhật"
                        );


                    if (category.icon) {

                        $("#imagePreview")
                            .attr(

                                "src",

                                contextPath
                                + "/image?fname="
                                + encodeURIComponent(
                                    category.icon
                                )
                            );


                        $("#imagePreviewArea")
                            .removeClass(
                                "d-none"
                            );

                    } else {

                        $("#imagePreview")
                            .attr(
                                "src",
                                ""
                            );


                        $("#imagePreviewArea")
                            .addClass(
                                "d-none"
                            );
                    }


                    categoryModal.show();
                },


            error:
                function (xhr) {

                    showMessage(
                        getErrorMessage(xhr),
                        false
                    );
                }
        });
    }


    /* =====================================
       ADD / UPDATE
       ===================================== */

    function saveCategory() {

        const categoryId =
            $("#categoryId")
                .val();


        const isUpdate =
            categoryId !== "";


        const formElement =
            document.getElementById(
                "categoryForm"
            );


        const formData =
            new FormData(
                formElement
            );


        let url;

        let method;


        if (isUpdate) {

            url =
                contextPath
                + "/api/category/updateCategory";

            method =
                "PUT";

        } else {

            formData.delete(
                "categoryId"
            );


            url =
                contextPath
                + "/api/category/addCategory";

            method =
                "POST";
        }


        $("#saveCategoryButton")
            .prop(
                "disabled",
                true
            );


        $.ajax({

            url:
                url,

            type:
                method,

            data:
                formData,

            processData:
                false,

            contentType:
                false,

            dataType:
                "json",


            success:
                function (response) {

                    categoryModal.hide();


                    showMessage(
                        response.message,
                        true
                    );


                    loadCategories();
                },


            error:
                function (xhr) {

                    showMessage(
                        getErrorMessage(xhr),
                        false
                    );
                },


            complete:
                function () {

                    $("#saveCategoryButton")
                        .prop(
                            "disabled",
                            false
                        );
                }
        });
    }


    /* =====================================
       DELETE
       ===================================== */

    function deleteCategory(
            categoryId) {


        const result =
            confirm(
                "Bạn có chắc muốn xóa Category này?"
            );


        if (!result) {
            return;
        }


        $.ajax({

            url:
                contextPath
                + "/api/category/deleteCategory",

            type:
                "DELETE",

            data: {
                categoryId:
                    categoryId
            },

            dataType:
                "json",


            success:
                function (response) {

                    showMessage(
                        response.message,
                        true
                    );


                    loadCategories();
                },


            error:
                function (xhr) {

                    showMessage(
                        getErrorMessage(xhr),
                        false
                    );
                }
        });
    }


    /* =====================================
       MESSAGE
       ===================================== */

    function showMessage(
            message,
            success) {


        const box =
            $("#messageBox");


        box.removeClass(
            "d-none "
            + "alert-success "
            + "alert-danger"
        );


        if (success) {

            box.addClass(
                "alert-success"
            );

        } else {

            box.addClass(
                "alert-danger"
            );
        }


        box.text(
            message
            || "Có lỗi xảy ra."
        );


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    }


    /* =====================================
       GET ERROR MESSAGE
       ===================================== */

    function getErrorMessage(xhr) {

        if (
            xhr.responseJSON
            && xhr.responseJSON.message
        ) {

            return xhr
                .responseJSON
                .message;
        }


        if (xhr.responseText) {

            try {

                const json =
                    JSON.parse(
                        xhr.responseText
                    );


                if (json.message) {

                    return json.message;
                }

            } catch (error) {

                console.error(
                    error
                );
            }
        }


        return (
            "Có lỗi xảy ra. HTTP "
            + xhr.status
        );
    }


    /* =====================================
       ESCAPE HTML
       ===================================== */

    function escapeHtml(value) {

        if (
            value === null
            || value === undefined
        ) {

            return "";
        }


        return String(value)

            .replaceAll(
                "&",
                "&amp;"
            )

            .replaceAll(
                "<",
                "&lt;"
            )

            .replaceAll(
                ">",
                "&gt;"
            )

            .replaceAll(
                '"',
                "&quot;"
            )

            .replaceAll(
                "'",
                "&#039;"
            );
    }

</script>

</body>

</html>