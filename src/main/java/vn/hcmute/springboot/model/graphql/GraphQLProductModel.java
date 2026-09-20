package vn.hcmute.springboot.model.graphql;

import vn.hcmute.springboot.entity.Product;

public class GraphQLProductModel {

    private int productId;

    private String productName;

    private String description;

    private Double price;

    private String image;

    private String createdAt;

    private GraphQLCategoryModel category;


    public GraphQLProductModel() {
    }


    public static GraphQLProductModel
            fromEntity(
                Product product) {

        if (product == null) {
            return null;
        }


        GraphQLProductModel model =
            new GraphQLProductModel();


        model.setProductId(
            product.getId()
        );


        model.setProductName(
            product.getName()
        );


        model.setDescription(
            product.getDescription()
        );


        if (product.getPrice() != null) {

            model.setPrice(
                product
                    .getPrice()
                    .doubleValue()
            );
        }


        model.setImage(
            product.getImage()
        );


        if (product.getCreatedAt() != null) {

            model.setCreatedAt(

                product
                    .getCreatedAt()
                    .toString()
            );
        }


        model.setCategory(

            GraphQLCategoryModel
                .fromEntity(
                    product.getCategory()
                )
        );


        return model;
    }


    public int getProductId() {
        return productId;
    }


    public void setProductId(
            int productId) {

        this.productId =
            productId;
    }


    public String getProductName() {
        return productName;
    }


    public void setProductName(
            String productName) {

        this.productName =
            productName;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(
            String description) {

        this.description =
            description;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(
            Double price) {

        this.price =
            price;
    }


    public String getImage() {
        return image;
    }


    public void setImage(
            String image) {

        this.image =
            image;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(
            String createdAt) {

        this.createdAt =
            createdAt;
    }


    public GraphQLCategoryModel
            getCategory() {

        return category;
    }


    public void setCategory(
            GraphQLCategoryModel category) {

        this.category =
            category;
    }
}