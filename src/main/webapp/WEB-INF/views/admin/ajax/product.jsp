<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">

    <title>Product CRUD AJAX</title>

    <style>
        .product-img {
            width: 100px;
            height: 75px;
            object-fit: cover;
            border-radius: 8px;
            border: 1px solid #ddd;
        }

        .preview-img {
            width: 180px;
            height: 130px;
            object-fit: cover;
            border-radius: 10px;
            border: 1px solid #ddd;
        }

        .product-description {
            max-width: 280px;
            white-space: normal;
            word-break: break-word;
        }
    </style>
</head>

<body>

<div class="d-flex justify-content-between align-items-center mb-4">

    <div>

        <h2 class="mb-1">
            Product CRUD bằng AJAX
        </h2>

        <p class="text-muted mb-0">
            Dữ liệu được tải và cập nhật thông qua REST API
        </p>

    </div>


    <button
        type="button"
        class="btn btn-primary"
        onclick="openCreateProductModal()">

        + Thêm Product

    </button>

</div>


<div
    id="productMessageBox"
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

            <th>Tên</th>

            <th>Mô tả</th>

            <th>Giá</th>

            <th>Category</th>

            <th>Ngày tạo</th>

            <th>Action</th>

        </tr>

        </thead>


        <tbody id="productTableBody">

        <tr>

            <td
                colspan="9"
                class="text-center">

                Đang tải dữ liệu...

            </td>

        </tr>

        </tbody>

    </table>

</div>


<!-- ============================= -->
<!-- MODAL THÊM / SỬA PRODUCT      -->
<!-- ============================= -->

<div
    class="modal fade"
    id="productModal"
    tabindex="-1"
    aria-hidden="true">

    <div class="modal-dialog modal-lg">

        <div class="modal-content">


            <form
                id="productForm"
                enctype="multipart/form-data">


                <div class="modal-header">

                    <h5
                        class="modal-title"
                        id="productModalTitle">

                        Thêm Product

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
                        id="productId"
                        name="productId">


                    <!-- TÊN PRODUCT -->

                    <div class="mb-3">

                        <label
                            for="productName"
                            class="form-label">

                            Tên Product

                        </label>


                        <input
                            type="text"
                            class="form-control"
                            id="productName"
                            name="productName"
                            required>

                    </div>


                    <!-- DESCRIPTION -->

                    <div class="mb-3">

                        <label
                            for="description"
                            class="form-label">

                            Mô tả

                        </label>


                        <textarea
                            class="form-control"
                            id="description"
                            name="description"
                            rows="4">
                        </textarea>

                    </div>


                    <div class="row">


                        <!-- PRICE -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="price"
                                class="form-label">

                                Giá

                            </label>


                            <input
                                type="number"
                                class="form-control"
                                id="price"
                                name="price"
                                min="0"
                                step="0.01"
                                required>

                        </div>


                        <!-- CATEGORY -->

                        <div class="col-md-6 mb-3">

                            <label
                                for="categoryId"
                                class="form-label">

                                Category

                            </label>


                            <select
                                class="form-select"
                                id="categoryId"
                                name="categoryId"
                                required>

                                <option value="">
                                    -- Chọn Category --
                                </option>

                            </select>

                        </div>

                    </div>


                    <!-- IMAGE -->

                    <div class="mb-3">

                        <label
                            for="imageFile"
                            class="form-label">

                            Ảnh Product

                        </label>


                        <input
                            type="file"
                            class="form-control"
                            id="imageFile"
                            name="imageFile"
                            accept=".jpg,.jpeg,.png,.gif,.webp">


                        <div class="form-text">

                            Khi sửa, nếu không chọn ảnh mới
                            thì hệ thống sẽ giữ ảnh hiện tại.

                        </div>

                    </div>


                    <!-- IMAGE PREVIEW -->

                    <div
                        id="productImagePreviewArea"
                        class="d-none">

                        <p class="mb-2">
                            Ảnh hiện tại:
                        </p>


                        <img
                            id="productImagePreview"
                            class="preview-img"
                            alt="Product">

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
                        id="saveProductButton">

                        Lưu

                    </button>

                </div>


            </form>

        </div>

    </div>

</div>


<script>

    let productModal;


    $(document).ready(function () {

        /*
         * Modal (hộp thoại nổi)
         * dùng để thêm và sửa Product.
         */
        productModal =
            bootstrap.Modal.getOrCreateInstance(
                document.getElementById(
                    "productModal"
                )
            );


        loadProducts();


        /*
         * Bắt sự kiện submit form.
         */
        $("#productForm").on(
            "submit",
            function (event) {

                event.preventDefault();

                saveProduct();
            }
        );


        /*
         * Preview ảnh khi người dùng chọn file.
         */
        $("#imageFile").on(
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

                        $("#productImagePreview")
                            .attr(
                                "src",
                                event.target.result
                            );


                        $("#productImagePreviewArea")
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


    /* =========================================
       LOAD PRODUCT
       ========================================= */

    function loadProducts() {

        $("#productTableBody").html(

            "<tr>"
            + "<td colspan='9' class='text-center'>"
            + "Đang tải dữ liệu..."
            + "</td>"
            + "</tr>"
        );


        $.ajax({

            url:
                contextPath
                + "/api/product",

            type:
                "GET",

            dataType:
                "json",


            success:
                function (response) {

                    if (!response.status) {

                        showProductMessage(
                            response.message,
                            false
                        );

                        return;
                    }


                    renderProducts(
                        response.body
                    );
                },


            error:
                function (xhr) {

                    showProductMessage(
                        getProductErrorMessage(xhr),
                        false
                    );


                    $("#productTableBody").html(

                        "<tr>"
                        + "<td colspan='9' "
                        + "class='text-center text-danger'>"
                        + "Không thể tải dữ liệu Product."
                        + "</td>"
                        + "</tr>"
                    );
                }
        });
    }


    /* =========================================
       RENDER PRODUCT
       ========================================= */

    function renderProducts(products) {

        if (
            !products
            || products.length === 0
        ) {

            $("#productTableBody").html(

                "<tr>"
                + "<td colspan='9' "
                + "class='text-center'>"
                + "Chưa có Product."
                + "</td>"
                + "</tr>"
            );

            return;
        }


        let html = "";


        products.forEach(
            function (
                product,
                index) {


                /* =============================
                   IMAGE
                   ============================= */

                let imageHtml =
                    "<span class='text-muted'>"
                    + "Chưa có ảnh"
                    + "</span>";


                if (product.image) {

                    const imageUrl =
                        contextPath
                        + "/product-image?fname="
                        + encodeURIComponent(
                            product.image
                        );


                    imageHtml =
                        "<img "
                        + "src='"
                        + imageUrl
                        + "' "
                        + "class='product-img' "
                        + "alt='Product'>";
                }


                /* =============================
                   DESCRIPTION
                   ============================= */

                let description =
                    product.description;


                if (
                    description === null
                    || description === undefined
                    || description === ""
                ) {

                    description = "";

                } else if (
                    description.length > 80
                ) {

                    description =
                        description.substring(
                            0,
                            80
                        )
                        + "...";
                }


                /* =============================
                   CREATED AT
                   ============================= */

                let createdAt =
                    formatDateTime(
                        product.createdAt
                    );


                /* =============================
                   TABLE ROW
                   ============================= */

                html +=

                    "<tr>"

                    + "<td>"
                    + (index + 1)
                    + "</td>"

                    + "<td>"
                    + product.productId
                    + "</td>"

                    + "<td>"
                    + imageHtml
                    + "</td>"

                    + "<td>"
                    + escapeProductHtml(
                        product.productName
                    )
                    + "</td>"

                    + "<td class='product-description'>"
                    + escapeProductHtml(
                        description
                    )
                    + "</td>"

                    + "<td class='text-nowrap'>"
                    + formatPrice(
                        product.price
                    )
                    + "</td>"

                    + "<td>"
                    + escapeProductHtml(
                        product.categoryName
                    )
                    + "</td>"

                    + "<td class='text-nowrap'>"
                    + escapeProductHtml(
                        createdAt
                    )
                    + "</td>"

                    + "<td class='text-nowrap'>"

                    + "<button "
                    + "type='button' "
                    + "class='btn btn-sm btn-warning me-1' "
                    + "onclick='openEditProductModal("
                    + product.productId
                    + ")'>"
                    + "Sửa"
                    + "</button>"

                    + "<button "
                    + "type='button' "
                    + "class='btn btn-sm btn-danger' "
                    + "onclick='deleteProduct("
                    + product.productId
                    + ")'>"
                    + "Xóa"
                    + "</button>"

                    + "</td>"

                    + "</tr>";
            }
        );


        $("#productTableBody")
            .html(html);
    }


    /* =========================================
       LOAD CATEGORY VÀO SELECT
       ========================================= */

    function loadCategoryOptions(
            selectedCategoryId,
            callback) {


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

                        showProductMessage(
                            response.message,
                            false
                        );

                        return;
                    }


                    const categories =
                        response.body;


                    let html =
                        "<option value=''>"
                        + "-- Chọn Category --"
                        + "</option>";


                    if (categories) {

                        categories.forEach(
                            function (category) {

                                html +=

                                    "<option value='"
                                    + category.categoryId
                                    + "'>"

                                    + escapeProductHtml(
                                        category.categoryName
                                    )

                                    + "</option>";
                            }
                        );
                    }


                    $("#categoryId")
                        .html(html);


                    if (
                        selectedCategoryId !== null
                        && selectedCategoryId !== undefined
                        && selectedCategoryId !== ""
                    ) {

                        $("#categoryId")
                            .val(
                                String(
                                    selectedCategoryId
                                )
                            );
                    }


                    if (
                        typeof callback
                        === "function"
                    ) {

                        callback();
                    }
                },


            error:
                function (xhr) {

                    showProductMessage(
                        getProductErrorMessage(xhr),
                        false
                    );
                }
        });
    }


    /* =========================================
       OPEN CREATE MODAL
       ========================================= */

    function openCreateProductModal() {

        $("#productForm")[0]
            .reset();


        $("#productId")
            .val("");


        $("#productModalTitle")
            .text(
                "Thêm Product"
            );


        $("#saveProductButton")
            .text(
                "Thêm"
            );


        $("#productImagePreview")
            .attr(
                "src",
                ""
            );


        $("#productImagePreviewArea")
            .addClass(
                "d-none"
            );


        loadCategoryOptions(
            null,
            function () {

                productModal.show();
            }
        );
    }


    /* =========================================
       OPEN EDIT MODAL
       ========================================= */

    function openEditProductModal(
            productId) {


        $.ajax({

            url:
                contextPath
                + "/api/product/getProduct",

            type:
                "POST",

            data: {
                id:
                    productId
            },

            dataType:
                "json",


            success:
                function (response) {

                    if (!response.status) {

                        showProductMessage(
                            response.message,
                            false
                        );

                        return;
                    }


                    const product =
                        response.body;


                    $("#productForm")[0]
                        .reset();


                    $("#productId")
                        .val(
                            product.productId
                        );


                    $("#productName")
                        .val(
                            product.productName
                        );


                    $("#description")
                        .val(
                            product.description
                            || ""
                        );


                    $("#price")
                        .val(
                            product.price
                        );


                    $("#productModalTitle")
                        .text(
                            "Cập nhật Product"
                        );


                    $("#saveProductButton")
                        .text(
                            "Cập nhật"
                        );


                    /* =============================
                       IMAGE
                       ============================= */

                    if (product.image) {

                        $("#productImagePreview")
                            .attr(

                                "src",

                                contextPath
                                + "/product-image?fname="
                                + encodeURIComponent(
                                    product.image
                                )
                            );


                        $("#productImagePreviewArea")
                            .removeClass(
                                "d-none"
                            );

                    } else {

                        $("#productImagePreview")
                            .attr(
                                "src",
                                ""
                            );


                        $("#productImagePreviewArea")
                            .addClass(
                                "d-none"
                            );
                    }


                    /*
                     * Load Category trước,
                     * sau đó chọn Category hiện tại.
                     */
                    loadCategoryOptions(

                        product.categoryId,

                        function () {

                            productModal.show();
                        }
                    );
                },


            error:
                function (xhr) {

                    showProductMessage(
                        getProductErrorMessage(xhr),
                        false
                    );
                }
        });
    }


    /* =========================================
       ADD / UPDATE PRODUCT
       ========================================= */

    function saveProduct() {

        const productId =
            $("#productId")
                .val();


        const isUpdate =
            productId !== "";


        const formElement =
            document.getElementById(
                "productForm"
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
                + "/api/product/updateProduct";

            method =
                "PUT";

        } else {

            /*
             * Khi thêm mới Product,
             * API không cần productId.
             */
            formData.delete(
                "productId"
            );


            url =
                contextPath
                + "/api/product/addProduct";

            method =
                "POST";
        }


        $("#saveProductButton")
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

                    productModal.hide();


                    showProductMessage(
                        response.message,
                        true
                    );


                    /*
                     * Gọi GET API lại để
                     * cập nhật bảng mà không reload trang.
                     */
                    loadProducts();
                },


            error:
                function (xhr) {

                    showProductMessage(
                        getProductErrorMessage(xhr),
                        false
                    );
                },


            complete:
                function () {

                    $("#saveProductButton")
                        .prop(
                            "disabled",
                            false
                        );
                }
        });
    }


    /* =========================================
       DELETE PRODUCT
       ========================================= */

    function deleteProduct(
            productId) {


        const result =
            confirm(
                "Bạn có chắc muốn xóa Product này?"
            );


        if (!result) {
            return;
        }


        $.ajax({

            url:
                contextPath
                + "/api/product/deleteProduct",

            type:
                "DELETE",

            data: {
                productId:
                    productId
            },

            dataType:
                "json",


            success:
                function (response) {

                    showProductMessage(
                        response.message,
                        true
                    );


                    loadProducts();
                },


            error:
                function (xhr) {

                    showProductMessage(
                        getProductErrorMessage(xhr),
                        false
                    );
                }
        });
    }


    /* =========================================
       MESSAGE
       ========================================= */

    function showProductMessage(
            message,
            success) {


        const box =
            $("#productMessageBox");


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


    /* =========================================
       ERROR MESSAGE
       ========================================= */

    function getProductErrorMessage(
            xhr) {


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


    /* =========================================
       FORMAT PRICE
       ========================================= */

    function formatPrice(
            value) {


        if (
            value === null
            || value === undefined
        ) {

            return "";
        }


        const number =
            Number(value);


        if (Number.isNaN(number)) {

            return escapeProductHtml(
                value
            );
        }


        return new Intl.NumberFormat(
            "vi-VN"
        ).format(number)
        + " đ";
    }


    /* =========================================
       FORMAT DATE
       ========================================= */

    function formatDateTime(
            value) {


        if (!value) {

            return "";
        }


        const date =
            new Date(value);


        if (
            Number.isNaN(
                date.getTime()
            )
        ) {

            return String(value);
        }


        return date.toLocaleString(
            "vi-VN"
        );
    }


    /* =========================================
       ESCAPE HTML
       ========================================= */

    function escapeProductHtml(
            value) {


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