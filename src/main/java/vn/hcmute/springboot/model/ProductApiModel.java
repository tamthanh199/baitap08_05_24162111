package vn.hcmute.springboot.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import vn.hcmute.springboot.entity.Product;

public class ProductApiModel {

    private int productId;

    private String productName;

    private String description;

    private BigDecimal price;

    private String image;

    private LocalDateTime createdAt;

    private int categoryId;

    private String categoryName;

    public ProductApiModel() {
    }

    public static ProductApiModel fromEntity(
            Product product) {

        ProductApiModel model =
            new ProductApiModel();

        model.setProductId(
            product.getId());

        model.setProductName(
            product.getName());

        model.setDescription(
            product.getDescription());

        model.setPrice(
            product.getPrice());

        model.setImage(
            product.getImage());

        model.setCreatedAt(
            product.getCreatedAt());

        if (product.getCategory() != null) {

            model.setCategoryId(
                product
                    .getCategory()
                    .getCategoryId());

            model.setCategoryName(
                product
                    .getCategory()
                    .getCategoryname());
        }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(
            BigDecimal price) {

        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(
            String image) {

        this.image = image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt =
            createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(
            int categoryId) {

        this.categoryId =
            categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(
            String categoryName) {

        this.categoryName =
            categoryName;
    }
}