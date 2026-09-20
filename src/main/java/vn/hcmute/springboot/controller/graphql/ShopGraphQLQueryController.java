package vn.hcmute.springboot.controller.graphql;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import vn.hcmute.springboot.entity.Category;
import vn.hcmute.springboot.entity.Product;
import vn.hcmute.springboot.model.graphql.GraphQLCategoryModel;
import vn.hcmute.springboot.model.graphql.GraphQLCategoryPage;
import vn.hcmute.springboot.model.graphql.GraphQLProductModel;
import vn.hcmute.springboot.model.graphql.GraphQLProductPage;
import vn.hcmute.springboot.service.ICategoryService;
import vn.hcmute.springboot.service.IProductService;

@Controller
public class ShopGraphQLQueryController {

    private final IProductService
        productService;

    private final ICategoryService
        categoryService;


    public ShopGraphQLQueryController(
            IProductService productService,
            ICategoryService categoryService) {

        this.productService =
            productService;

        this.categoryService =
            categoryService;
    }

    @QueryMapping
    public List<GraphQLProductModel>
            productsByPriceAsc() {

        return productService
            .getAllByPriceAsc()
            .stream()
            .map(
                GraphQLProductModel
                    ::fromEntity
            )
            .toList();
    }

    @QueryMapping
    public List<GraphQLProductModel>
            productsByCategory(

        @Argument
        int categoryId) {

        return productService
            .getByCategoryId(
                categoryId
            )
            .stream()
            .map(
                GraphQLProductModel
                    ::fromEntity
            )
            .toList();
    }

    @QueryMapping
    public List<GraphQLCategoryModel>
            categories() {

        return categoryService
            .findAll()
            .stream()
            .map(
                GraphQLCategoryModel
                    ::fromEntity
            )
            .toList();
    }


    @QueryMapping
    public GraphQLCategoryModel
            categoryById(

        @Argument
        int id) {

        return GraphQLCategoryModel
            .fromEntity(

                categoryService
                    .getById(id)
            );
    }

    @QueryMapping
    public GraphQLProductModel
            productById(

        @Argument
        int id) {

        return GraphQLProductModel
            .fromEntity(

                productService
                    .getById(id)
            );
    }

    @QueryMapping
    public GraphQLProductPage
            productPage(

        @Argument
        String keyword,

        @Argument
        Integer page,

        @Argument
        Integer size) {


        int pageNumber =
            normalizePage(page);


        int pageSize =
            normalizeSize(size);


        Pageable pageable =
            PageRequest.of(

                pageNumber,

                pageSize,

                Sort.by(
                    Sort.Direction.DESC,
                    "id"
                )
            );


        Page<Product> result;


        if (
            keyword == null
            || keyword.isBlank()
        ) {

            result =
                productService
                    .getPage(
                        pageable
                    );

        } else {

            result =
                productService
                    .searchByName(

                        keyword.trim(),

                        pageable
                    );
        }


        return GraphQLProductPage
            .fromPage(result);
    }

    @QueryMapping
    public GraphQLCategoryPage
            categoryPage(

        @Argument
        String keyword,

        @Argument
        Integer page,

        @Argument
        Integer size) {


        int pageNumber =
            normalizePage(page);


        int pageSize =
            normalizeSize(size);


        Pageable pageable =
            PageRequest.of(

                pageNumber,

                pageSize,

                Sort.by(
                    Sort.Direction.DESC,
                    "categoryId"
                )
            );


        Page<Category> result;


        if (
            keyword == null
            || keyword.isBlank()
        ) {

            result =
                categoryService
                    .findAll(
                        pageable
                    );

        } else {

            result =
                categoryService
                    .findByCategorynameContaining(

                        keyword.trim(),

                        pageable
                    );
        }


        return GraphQLCategoryPage
            .fromPage(result);
    }


    private int normalizePage(
            Integer page) {

        if (
            page == null
            || page < 0
        ) {

            return 0;
        }

        return page;
    }


    private int normalizeSize(
            Integer size) {

        if (
            size == null
            || size <= 0
        ) {

            return 5;
        }


        return Math.min(
            size,
            50
        );
    }
}