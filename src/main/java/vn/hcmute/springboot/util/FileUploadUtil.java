package vn.hcmute.springboot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public final class FileUploadUtil {
    private static final Path ROOT = Paths.get(System.getProperty("user.home"), "ServletCrudMvcUploads");
    private static final Path CATEGORY_DIRECTORY = ROOT.resolve("category");
    private static final Path PROFILE_DIRECTORY = ROOT.resolve("profile");
    private static final Path PRODUCT_DIRECTORY = ROOT.resolve("product");

    private FileUploadUtil() { }

    public static String saveCategoryImage(MultipartFile file) throws IOException { return saveImage(file, CATEGORY_DIRECTORY); }
    public static String saveProfileImage(MultipartFile file) throws IOException { return saveImage(file, PROFILE_DIRECTORY); }
    public static String saveProductImage(MultipartFile file) throws IOException { return saveImage(file, PRODUCT_DIRECTORY); }

    private static String saveImage(MultipartFile file, Path directory) throws IOException {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) return null;
        String originalFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex < 0) throw new IOException("File ảnh phải có phần mở rộng.");
        String extension = originalFileName.substring(dotIndex).toLowerCase(Locale.ROOT);
        if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png")
                && !extension.equals(".gif") && !extension.equals(".webp")) {
            throw new IOException("Chỉ chấp nhận JPG, JPEG, PNG, GIF hoặc WEBP.");
        }
        Files.createDirectories(directory);
        String savedFileName = UUID.randomUUID() + extension;
        Path destination = directory.resolve(savedFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        return savedFileName;
    }

    public static Path getCategoryImagePath(String fileName) { return safeResolve(CATEGORY_DIRECTORY, fileName); }
    public static Path getProfileImagePath(String fileName) { return safeResolve(PROFILE_DIRECTORY, fileName); }
    public static Path getProductImagePath(String fileName) { return safeResolve(PRODUCT_DIRECTORY, fileName); }
    public static void deleteCategoryImage(String fileName) throws IOException { deleteImage(fileName, CATEGORY_DIRECTORY); }
    public static void deleteProfileImage(String fileName) throws IOException { deleteImage(fileName, PROFILE_DIRECTORY); }
    public static void deleteProductImage(String fileName) throws IOException { deleteImage(fileName, PRODUCT_DIRECTORY); }

    private static Path safeResolve(Path directory, String fileName) {
        Path normalizedDirectory = directory.toAbsolutePath().normalize();
        Path imagePath = normalizedDirectory.resolve(fileName).normalize();
        if (!imagePath.startsWith(normalizedDirectory)) throw new IllegalArgumentException("Đường dẫn file không hợp lệ.");
        return imagePath;
    }

    private static void deleteImage(String fileName, Path directory) throws IOException {
        if (fileName == null || fileName.isBlank()) return;
        Files.deleteIfExists(safeResolve(directory, fileName));
    }
}
