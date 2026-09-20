package vn.hcmute.springboot.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryModel {
    private int categoryId;

    @NotBlank(message = "Tên category không được để trống")
    @Size(max = 255, message = "Tên category tối đa 255 ký tự")
    private String categoryname;

    @Size(max = 255, message = "Tên file ảnh tối đa 255 ký tự")
    private String images;

    private int status = 1;
    private boolean isEdit;

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public boolean getIsEdit() { return isEdit; }
    public void setIsEdit(boolean isEdit) { this.isEdit = isEdit; }
}
