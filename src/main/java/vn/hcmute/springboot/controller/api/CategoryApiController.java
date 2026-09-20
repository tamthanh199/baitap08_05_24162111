package vn.hcmute.springboot.controller.api;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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
import vn.hcmute.springboot.model.ApiResponse;
import vn.hcmute.springboot.model.CategoryApiModel;
import vn.hcmute.springboot.service.ICategoryService;
import vn.hcmute.springboot.util.FileUploadUtil;

@RestController
@RequestMapping("/api/category")

@Tag(
    name = "Category API",
    description = "REST API CRUD cho Category"
)

public class CategoryApiController {

    private final ICategoryService categoryService;

    public CategoryApiController(
            ICategoryService categoryService) {

        this.categoryService =
            categoryService;
    }

    @GetMapping
    @Operation(
        summary = "Lấy tất cả Category"
    )
    public ResponseEntity<ApiResponse>
            getAllCategory() {

        List<CategoryApiModel> categories =
            categoryService
                .findAll()
                .stream()
                .map(CategoryApiModel::fromEntity)
                .toList();

        return ResponseEntity.ok(

            new ApiResponse(
                true,
                "Thành công",
                categories
            )
        );
    }

    @PostMapping("/getCategory")
    @Operation(
        summary = "Lấy một Category theo ID"
    )
    public ResponseEntity<ApiResponse>
            getCategory(
                @RequestParam("id") int id) {

        Category category =
            categoryService.getById(id);

        if (category == null) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                    new ApiResponse(
                        false,
                        "Không tìm thấy Category",
                        null
                    )
                );
        }

        return ResponseEntity.ok(

            new ApiResponse(
                true,
                "Thành công",
                CategoryApiModel
                    .fromEntity(category)
            )
        );
    }

    @PostMapping(
        value = "/addCategory",
        consumes = "multipart/form-data"
    )
    @Operation(
        summary = "Thêm Category"
    )
    public ResponseEntity<ApiResponse>
            addCategory(

        @RequestParam("categoryName")
        String categoryName,

        @RequestParam(
            value = "status",
            defaultValue = "1")
        int status,

        @RequestParam(
            value = "icon",
            required = false)
        MultipartFile icon) {

        if (!StringUtils.hasText(
                categoryName)) {

            return ResponseEntity
                .badRequest()
                .body(

                    new ApiResponse(
                        false,
                        "Tên Category không được để trống",
                        null
                    )
                );
        }

        Category category =
            new Category();

        category.setCategoryname(
            categoryName.trim());

        category.setStatus(
            status == 0 ? 0 : 1);

        try {

            String imageName =
                FileUploadUtil
                    .saveCategoryImage(icon);

            category.setImages(
                imageName);

            Category saved =
                categoryService
                    .save(category);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(

                    new ApiResponse(
                        true,
                        "Thêm Category thành công",
                        CategoryApiModel
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
        value = "/updateCategory",
        consumes = "multipart/form-data"
    )
    @Operation(
        summary = "Cập nhật Category"
    )
    public ResponseEntity<ApiResponse>
            updateCategory(

        @RequestParam("categoryId")
        int categoryId,

        @RequestParam("categoryName")
        String categoryName,

        @RequestParam(
            value = "status",
            defaultValue = "1")
        int status,

        @RequestParam(
            value = "icon",
            required = false)
        MultipartFile icon) {

        Category category =
            categoryService
                .getById(categoryId);

        if (category == null) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                    new ApiResponse(
                        false,
                        "Không tìm thấy Category",
                        null
                    )
                );
        }

        if (!StringUtils.hasText(
                categoryName)) {

            return ResponseEntity
                .badRequest()
                .body(

                    new ApiResponse(
                        false,
                        "Tên Category không được để trống",
                        null
                    )
                );
        }

        String oldImage =
            category.getImages();

        try {

            String newImage =
                FileUploadUtil
                    .saveCategoryImage(icon);

            category.setCategoryname(
                categoryName.trim());

            category.setStatus(
                status == 0 ? 0 : 1);

            if (newImage != null) {

                category.setImages(
                    newImage);
            }

            Category saved =
                categoryService
                    .save(category);

            if (newImage != null
                    && oldImage != null
                    && !oldImage.isBlank()) {

                try {

                    FileUploadUtil
                        .deleteCategoryImage(
                            oldImage);

                } catch (IOException ignored) {
                }
            }

            return ResponseEntity.ok(

                new ApiResponse(
                    true,
                    "Cập nhật Category thành công",
                    CategoryApiModel
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

    @DeleteMapping("/deleteCategory")
    @Operation(
        summary = "Xóa Category"
    )
    public ResponseEntity<ApiResponse>
            deleteCategory(

        @RequestParam("categoryId")
        int categoryId) {

        Category category =
            categoryService
                .getById(categoryId);

        if (category == null) {

            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(

                    new ApiResponse(
                        false,
                        "Không tìm thấy Category",
                        null
                    )
                );
        }

        String image =
            category.getImages();

        try {

            categoryService
                .deleteById(categoryId);

            if (image != null
                    && !image.isBlank()) {

                try {

                    FileUploadUtil
                        .deleteCategoryImage(
                            image);

                } catch (IOException ignored) {
                }
            }

            return ResponseEntity.ok(

                new ApiResponse(
                    true,
                    "Xóa Category thành công",
                    null
                )
            );

        } catch (
            DataIntegrityViolationException e) {

            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(

                    new ApiResponse(
                        false,
                        "Không thể xóa Category đang được Product sử dụng",
                        null
                    )
                );
        }
    }
}