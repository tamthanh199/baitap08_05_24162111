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
@Table(name = "app_user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(
            name = "username",
            nullable = false,
            unique = true,
            columnDefinition = "NVARCHAR(100)"
    )
    private String username;

    @Column(
            name = "user_password",
            nullable = false,
            columnDefinition = "NVARCHAR(255)"
    )
    private String password;

    @Column(
            name = "full_name",
            columnDefinition = "NVARCHAR(255)"
    )
    private String fullName;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "NVARCHAR(255)"
    )
    private String email;

    @Column(
            name = "phone",
            columnDefinition = "VARCHAR(30)"
    )
    private String phone;

    @Column(
            name = "images",
            columnDefinition = "NVARCHAR(255)"
    )
    private String images;

    @Column(
            name = "role",
            columnDefinition = "NVARCHAR(20) NULL"
    )
    private String role = ROLE_USER;

    public User() {
        this.role = ROLE_USER;
    }

    public User(
            String username,
            String password,
            String fullName,
            String email,
            String phone) {

        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = ROLE_USER;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (ROLE_ADMIN.equalsIgnoreCase(role)) {
            this.role = ROLE_ADMIN;
        } else {
            this.role = ROLE_USER;
        }
    }

    public boolean isAdmin() {
        return ROLE_ADMIN.equalsIgnoreCase(role);
    }
}