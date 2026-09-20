package vn.hcmute.springboot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletResponse;
import vn.hcmute.springboot.util.FileUploadUtil;

@Controller
public class ImageController {

    @GetMapping("/image")
    public void category(@RequestParam("fname") String fileName, HttpServletResponse response) throws IOException {
        write(FileUploadUtil.getCategoryImagePath(safe(fileName)), response);
    }

    @GetMapping("/product-image")
    public void product(@RequestParam("fname") String fileName, HttpServletResponse response) throws IOException {
        write(FileUploadUtil.getProductImagePath(safe(fileName)), response);
    }

    @GetMapping("/profile-image")
    public void profile(@RequestParam("fname") String fileName, HttpServletResponse response) throws IOException {
        write(FileUploadUtil.getProfileImagePath(safe(fileName)), response);
    }

    private String safe(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank() || fileName.contains("/") || fileName.contains("\\"))
            throw new IOException("File không hợp lệ");
        return fileName;
    }

    private void write(Path path, HttpServletResponse response) throws IOException {
        if (!Files.exists(path) || !Files.isRegularFile(path)) { response.sendError(404); return; }
        String contentType = Files.probeContentType(path);
        response.setContentType(contentType == null ? "application/octet-stream" : contentType);
        response.setContentLengthLong(Files.size(path));
        Files.copy(path, response.getOutputStream());
    }
}
