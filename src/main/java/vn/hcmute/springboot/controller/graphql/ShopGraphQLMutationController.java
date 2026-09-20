package vn.hcmute.springboot.controller.graphql;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import vn.hcmute.springboot.entity.Category;
import vn.hcmute.springboot.entity.Product;
import vn.hcmute.springboot.model.graphql.GraphQLCategoryModel;
import vn.hcmute.springboot.model.graphql.GraphQLProductModel;
import vn.hcmute.springboot.model.graphql.input.CategoryInput;
import vn.hcmute.springboot.model.graphql.input.ProductInput;
import vn.hcmute.springboot.service.ICategoryService;
import vn.hcmute.springboot.service.IProductService;
import vn.hcmute.springboot.util.FileUploadUtil;

@Controller
public class ShopGraphQLMutationController {

    private final ICategoryService categoryService;

    private final IProductService productService;


    public ShopGraphQLMutationController(
            ICategoryService categoryService,
            IProductService productService) {

        this.categoryService =
            categoryService;

        this.productService =
            productService;
    }

    @MutationMapping
    public GraphQLCategoryModel createCategory(
            @Argument CategoryInput input) {

        validateCategoryInput(input);


        Category category =
            new Category();


        category.setCategoryname(
            input.categoryName().trim()
        );


        category.setImages(
            normalizeString(
                input.icon()
            )
        );


        category.setStatus(
            normalizeStatus(
                input.status()
            )
        );


        Category saved =
            categoryService.save(
                category
            );


        return GraphQLCategoryModel
            .fromEntity(saved);
    }

    @MutationMapping
    public GraphQLCategoryModel updateCategory(
            @Argument int id,
            @Argument CategoryInput input) {

        Category category =
            categoryService.getById(id);


        if (category == null) {

            throw new IllegalArgumentException(
                "Không tìm thấy Category có ID = "
                + id
            );
        }


        validateCategoryInput(input);


        category.setCategoryname(
            input.categoryName().trim()
        );

        if (input.icon() != null) {

            category.setImages(
                normalizeString(
                    input.icon()
                )
            );
        }


        category.setStatus(
            normalizeStatus(
                input.status()
            )
        );


        Category saved =
            categoryService.save(
                category
            );


        return GraphQLCategoryModel
            .fromEntity(saved);
    }

    @MutationMapping
    public boolean deleteCategory(
            @Argument int id) {

        Category category =
            categoryService.getById(id);


        if (category == null) {

            throw new IllegalArgumentException(
                "Không tìm thấy Category có ID = "
                + id
            );
        }


        String oldImage =
            category.getImages();


        try {

            categoryService
                .deleteById(id);

        } catch (
            DataIntegrityViolationException exception
        ) {

            throw new IllegalArgumentException(
                "Không thể xóa Category đang được Product sử dụng."
            );
        }


        if (
            oldImage != null
            && !oldImage.isBlank()
        ) {

            try {

                FileUploadUtil
                    .deleteCategoryImage(
                        oldImage
                    );

            } catch (IOException ignored) {
            }
        }


        return true;
    }

    @MutationMapping
    public GraphQLProductModel createProduct(
            @Argument ProductInput input) {

        validateProductInput(input);


        Category category =
            categoryService.getById(
                input.categoryId()
            );


        if (category == null) {

            throw new IllegalArgumentException(
                "Category không tồn tại."
            );
        }


        Product product =
            new Product();


        product.setName(
            input.productName().trim()
        );


        product.setDescription(
            normalizeString(
                input.description()
            )
        );


        product.setPrice(

            BigDecimal.valueOf(
                input.price()
            )
        );


        product.setImage(
            normalizeString(
                input.image()
            )
        );


        product.setCategory(
            category
        );


        Product saved =
            productService.save(
                product
            );


        return GraphQLProductModel
            .fromEntity(saved);
    }

    @MutationMapping
    public GraphQLProductModel updateProduct(
            @Argument int id,
            @Argument ProductInput input) {

        Product product =
            productService.getById(id);


        if (product == null) {

            throw new IllegalArgumentException(
                "Không tìm thấy Product có ID = "
                + id
            );
        }


        validateProductInput(input);


        Category category =
            categoryService.getById(
                input.categoryId()
            );


        if (category == null) {

            throw new IllegalArgumentException(
                "Category không tồn tại."
            );
        }


        product.setName(
            input.productName().trim()
        );


        product.setDescription(
            normalizeString(
                input.description()
            )
        );


        product.setPrice(

            BigDecimal.valueOf(
                input.price()
            )
        );

        if (input.image() != null) {

            product.setImage(
                normalizeString(
                    input.image()
                )
            );
        }


        product.setCategory(
            category
        );


        Product saved =
            productService.save(
                product
            );


        return GraphQLProductModel
            .fromEntity(saved);
    }

    @MutationMapping
    public boolean deleteProduct(
            @Argument int id) {

        Product product =
            productService.getById(id);


        if (product == null) {

            throw new IllegalArgumentException(
                "Không tìm thấy Product có ID = "
                + id
            );
        }


        String oldImage =
            product.getImage();


        productService.delete(id);


        if (
            oldImage != null
            && !oldImage.isBlank()
        ) {

            try {

                FileUploadUtil
                    .deleteProductImage(
                        oldImage
                    );

            } catch (IOException ignored) {
            }
        }


        return true;
    }


    /*
     * =====================================
     * VALIDATION
     * =====================================
     */

    private void validateCategoryInput(
            CategoryInput input) {

        if (input == null) {

            throw new IllegalArgumentException(
                "Dữ liệu Category không hợp lệ."
            );
        }


        if (
            !StringUtils.hasText(
                input.categoryName()
            )
        ) {

            throw new IllegalArgumentException(
                "Tên Category không được để trống."
            );
        }
    }


    private void validateProductInput(
            ProductInput input) {

        if (input == null) {

            throw new IllegalArgumentException(
                "Dữ liệu Product không hợp lệ."
            );
        }


        if (
            !StringUtils.hasText(
                input.productName()
            )
        ) {

            throw new IllegalArgumentException(
                "Tên Product không được để trống."
            );
        }


        if (
            input.price() == null
            || input.price() < 0
        ) {

            throw new IllegalArgumentException(
                "Giá Product phải lớn hơn hoặc bằng 0."
            );
        }


        if (
            input.categoryId() == null
            || input.categoryId() <= 0
        ) {

            throw new IllegalArgumentException(
                "Category không hợp lệ."
            );
        }
    }


    private int normalizeStatus(
            Integer status) {

        if (status == null) {
            return 1;
        }


        return status == 0
            ? 0
            : 1;
    }


    private String normalizeString(
            String value) {

        if (value == null) {
            return null;
        }


        String result =
            value.trim();


        return result.isEmpty()
            ? null
            : result;
    }
}