package vn.hcmute.springboot.model;

import vn.hcmute.springboot.entity.Category;

public class CategoryApiModel {

    private int categoryId;

    private String categoryName;

    private String icon;

    private int status;

    public CategoryApiModel() {
    }

    public CategoryApiModel(
            int categoryId,
            String categoryName,
            String icon,
            int status) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.icon = icon;
        this.status = status;
    }

    public static CategoryApiModel fromEntity(
            Category category) {

        return new CategoryApiModel(

            category.getCategoryId(),

            category.getCategoryname(),

            category.getImages(),

            category.getStatus()
        );
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(
            String categoryName) {

        this.categoryName =
            categoryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}