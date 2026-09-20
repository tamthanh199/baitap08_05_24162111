package vn.hcmute.springboot.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "categoryname", columnDefinition = "NVARCHAR(255) NULL")
    private String categoryname;

    @Column(name = "images", columnDefinition = "NVARCHAR(255) NULL")
    private String images;

    @Column(name = "status")
    private int status = 1;

    public Category() {
    }

    public Category(int categoryId, String categoryname, String images, int status) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
        this.images = images;
        this.status = status;
    }

    public Category(String name, String icon) {
        this.categoryname = name;
        this.images = icon;
        this.status = 1;
    }

    public Category(int id, String name, String icon) {
        this.categoryId = id;
        this.categoryname = name;
        this.images = icon;
        this.status = 1;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public String getName() {
        return categoryname;
    }

    public void setName(String name) {
        this.categoryname = name;
    }

    public String getIcon() {
        return images;
    }

    public void setIcon(String icon) {
        this.images = icon;
    }
}