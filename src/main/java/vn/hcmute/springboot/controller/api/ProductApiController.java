package vn.hcmute.springboot.controller.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import vn.hcmute.springboot.entity.Category;
import vn.hcmute.springboot.entity.Product;
import vn.hcmute.springboot.model.ApiResponse;
import vn.hcmute.springboot.model.ProductApiModel;
import vn.hcmute.springboot.service.ICategoryService;
import vn.hcmute.springboot.service.IProductService;
import vn.hcmute.springboot.util.FileUploadUtil;

@RestController
@RequestMapping("/api/product")

@Tag(
    name = "Product API",
    description = "REST API CRUD cho Product"
)

public class ProductApiController {

    private final IProductService productService;

    private final ICategoryService categoryService;

    public ProductApiController(
            IProductService productService,
            ICategoryService categoryService) {

        this.productService =
            productService;

        this.categoryService =
            categoryService;
    }

    @GetMapping
    @Operation(
        summary = "Lấy tất cả Product"
    )
    public ResponseEntity<ApiResponse>
            getAllProduct() {

        List<ProductApiModel> products =
            productService
                .getAll()
                .stream()
                .map(ProductApiModel::fromEntity)
                .toList();

        return ResponseEntity.ok(

            new ApiResponse(
                true,
                "Thành công",
                products
            )
        );
    }

    @PostMapping("/getProduct")
    @Operation(
        summary = "Lấy Product theo ID"
    )
    public ResponseEntity<ApiResponse>
            getProduct(
                @RequestParam("id") int id) {

        Product product =
            productService.getById(id);

        if (product == null) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                    new ApiResponse(
                        false,
                        "Không tìm thấy Product",
                        null
                    )
                );
        }

        return ResponseEntity.ok(

            new ApiResponse(
                true,
                "Thành công",
                ProductApiModel
                    .fromEntity(product)
            )
        );
    }

    @PostMapping(
        value = "/addProduct",
        consumes = "multipart/form-data"
    )
    @Operation(
        summary = "Thêm Product"
    )
    public ResponseEntity<ApiResponse>
            addProduct(

        @RequestParam("productName")
        String productName,

        @RequestParam(
            value = "description",
            defaultValue = "")
        String description,

        @RequestParam("price")
        BigDecimal price,

        @RequestParam("categoryId")
        int categoryId,

        @RequestParam(
            value = "imageFile",
            required = false)
        MultipartFile imageFile) {

        String validationMessage =
            validateProduct(
                productName,
                price,
                categoryId
            );

        if (validationMessage != null) {

            return ResponseEntity
                .badRequest()
                .body(

                    new ApiResponse(
                        false,
                        validationMessage,
                        null
                    )
                );
        }

        Category category =
            categoryService
                .getById(categoryId);

        Product product =
            new Product();

        product.setName(
            productName.trim());

        product.setDescription(
            description == null
                ? ""
                : description.trim());

        product.setPrice(price);

        product.setCategory(
            category);

        try {

            product.setImage(

                FileUploadUtil
                    .saveProductImage(
                        imageFile)
            );

            Product saved =
                productService
                    .save(product);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(

                    new ApiResponse(
                        true,
                        "Thêm Product thành công",
                        ProductApiModel
                            .fromEntity(saved)
                    )
                );

        } catch (IOException e) {

            return ResponseEntity
                .badRequest()
                .body(

                    new ApiResponse(
                        false,
                        e.getMessage(),
                        null
                    )
                );
        }
    }

    @PutMapping(
        value = "/updateProduct",
        consumes = "multipart/form-data"
    )
    @Operation(
        summary = "Cập nhật Product"
    )
    public ResponseEntity<ApiResponse>
            updateProduct(

        @RequestParam("productId")
        int productId,

        @RequestParam("productName")
        String productName,

        @RequestParam(
            value = "description",
            defaultValue = "")
        String description,

        @RequestParam("price")
        BigDecimal price,

        @RequestParam("categoryId")
        int categoryId,

        @RequestParam(
            value = "imageFile",
            required = false)
        MultipartFile imageFile) {

        Product product =
            productService
                .getById(productId);

        if (product == null) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                    new ApiResponse(
                        false,
                        "Không tìm thấy Product",
                        null
                    )
                );
        }

        String validationMessage =
            validateProduct(
                productName,
                price,
                categoryId
            );

        if (validationMessage != null) {

            return ResponseEntity
                .badRequest()
                .body(

                    new ApiResponse(
                        false,
                        validationMessage,
                        null
                    )
                );
        }

        String oldImage =
            product.getImage();

        try {

            String newImage =
                FileUploadUtil
                    .saveProductImage(
                        imageFile);

            product.setName(
                productName.trim());

            product.setDescription(
                description == null
                    ? ""
                    : description.trim());

            product.setPrice(
                price);

            product.setCategory(

                categoryService
                    .getById(categoryId)
            );

            if (newImage != null) {

                product.setImage(
                    newImage);
            }

            Product saved =
                productService
                    .save(product);

            if (newImage != null
                    && oldImage != null
                    && !oldImage.isBlank()) {

                try {

                    FileUploadUtil
                        .deleteProductImage(
                            oldImage);

                } catch (IOException ignored) {
                }
            }

            return ResponseEntity.ok(

                new ApiResponse(
                    true,
                    "Cập nhật Product thành công",
                    ProductApiModel
                        .fromEntity(saved)
                )
            );

        } catch (IOException e) {

            return ResponseEntity
                .badRequest()
                .body(

                    new ApiResponse(
                        false,
                        e.getMessage(),
                        null
                    )
                );
        }
    }

    @DeleteMapping("/deleteProduct")
    @Operation(
        summary = "Xóa Product"
    )
    public ResponseEntity<ApiResponse>
            deleteProduct(

        @RequestParam("productId")
        int productId) {

        Product product =
            productService
                .getById(productId);

        if (product == null) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                    new ApiResponse(
                        false,
                        "Không tìm thấy Product",
                        null
                    )
                );
        }

        String image =
            product.getImage();

        productService
            .delete(productId);

        if (image != null
                && !image.isBlank()) {

            try {

                FileUploadUtil
                    .deleteProductImage(
                        image);

            } catch (IOException ignored) {
            }
        }

        return ResponseEntity.ok(

            new ApiResponse(
                true,
                "Xóa Product thành công",
                null
            )
        );
    }

    private String validateProduct(
            String productName,
            BigDecimal price,
            int categoryId) {

        if (!StringUtils.hasText(
                productName)) {

            return
                "Tên Product không được để trống";
        }

        if (price == null
                || price.compareTo(
                    BigDecimal.ZERO) < 0) {

            return
                "Giá phải lớn hơn hoặc bằng 0";
        }

        if (categoryService
                .getById(categoryId) == null) {

            return
                "Category không tồn tại";
        }

        return null;
    }
}