# Baitap08_04_24162111

## 1. Tạo database

Mở SQL Server và chạy:

```sql
IF DB_ID('webst2') IS NULL
BEGIN
    CREATE DATABASE webst2;
END
GO
```

Database sử dụng:

webst2

---

## 2. Cấu hình kết nối SQL Server

Mở file:

src/main/resources/application.properties

YOUR_SA_PASSWORD

bằng mật khẩu tài khoản `sa` trên máy đang chạy project.

Ứng dụng chạy ở port:

8090


## 3. URL kiểm tra

Trang chủ:

http://localhost:8090/home

Swagger 3 / OpenAPI:

http://localhost:8090/swagger-ui/index.html

Category CRUD bằng AJAX:

http://localhost:8090/admin/ajax/categories

Product CRUD bằng AJAX:

http://localhost:8090/admin/ajax/products

Category CRUD MVC:

http://localhost:8090/admin/categories

Product CRUD MVC:

http://localhost:8090/admin/product/list

Các đường dẫn `/admin/**` yêu cầu đăng nhập bằng tài khoản có Role `ADMIN`.

---
