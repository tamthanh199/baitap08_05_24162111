# Baitap08_05_24162111

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

```text
webst2
```

Các bảng chính:

```text
categories
products
app_user
```

---

## 2. Cấu hình kết nối SQL Server

Mở file:

```text
src/main/resources/application.properties
```

Kiểm tra phần cấu hình SQL Server:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=webst2;encrypt=false;trustServerCertificate=true;sslProtocol=TLSv1.2;characterEncoding=UTF-8
spring.datasource.username=sa
spring.datasource.password=YOUR_SA_PASSWORD
```

Thay:

```text
YOUR_SA_PASSWORD
```

bằng mật khẩu tài khoản `sa` trên máy đang chạy project.

Ứng dụng chạy ở port:

```text
8090
```

---

## 3. URL kiểm tra

GraphiQL:

```text
http://localhost:8090/graphiql
```

Trang Home sử dụng Thymeleaf + AJAX + GraphQL:

```text
http://localhost:8090/graphql-ui/home
```

Quản lý Category bằng Thymeleaf + AJAX + GraphQL:

```text
http://localhost:8090/admin/graphql/categories
```

Quản lý Product bằng Thymeleaf + AJAX + GraphQL:

```text
http://localhost:8090/admin/graphql/products
```

Các đường dẫn:

```text
/admin/**
```

yêu cầu đăng nhập bằng tài khoản có Role `ADMIN`.

---

## 4. Chức năng GraphQL

### Query

- Hiển thị tất cả Product có price từ thấp đến cao.
- Lấy tất cả Product thuộc một Category.
- Lấy Product theo ID.
- Lấy Category theo ID.
- Tìm kiếm Product theo tên.
- Phân trang Product.
- Tìm kiếm Category theo tên.
- Phân trang Category.

### Mutation

Category:

- Thêm Category.
- Cập nhật Category.
- Xóa Category.

Product:

- Thêm Product.
- Cập nhật Product.
- Xóa Product.

---

## 5. Chức năng Home

Trang:

```text
http://localhost:8090/graphql-ui/home
```

có các chức năng:

- Hiển thị Product theo giá từ thấp đến cao.
- Hiển thị tên, giá, mô tả, hình ảnh và Category của Product.
- Lọc Product theo một Category.
- Dữ liệu được lấy bằng GraphQL.
- Giao diện được render bằng AJAX mà không cần tải lại toàn bộ trang.

---

## 6. Quản lý Category

Trang:

```text
http://localhost:8090/admin/graphql/categories
```

Các chức năng:

- Hiển thị danh sách Category.
- Thêm Category.
- Cập nhật Category.
- Xóa Category.
- Tìm kiếm Category theo tên.
- Phân trang Category.
- Thay đổi số lượng phần tử trên mỗi trang.

Dữ liệu được thao tác bằng GraphQL Query và Mutation, giao diện sử dụng Thymeleaf và AJAX.

---

## 7. Quản lý Product

Trang:

```text
http://localhost:8090/admin/graphql/products
```

Các chức năng:

- Hiển thị danh sách Product.
- Thêm Product.
- Cập nhật Product.
- Xóa Product.
- Tìm kiếm Product theo tên.
- Phân trang Product.
- Thay đổi số lượng phần tử trên mỗi trang.
- Chọn Category cho Product.
- Hiển thị hình ảnh Product.

Dữ liệu được thao tác bằng GraphQL Query và Mutation, giao diện sử dụng Thymeleaf và AJAX.

---

## 8. GraphQL Endpoint

GraphQL Endpoint:

```text
http://localhost:8090/graphql
```

GraphiQL:

```text
http://localhost:8090/graphiql
```

Schema GraphQL nằm tại:

```text
src/main/resources/graphql/schema.graphqls
```
