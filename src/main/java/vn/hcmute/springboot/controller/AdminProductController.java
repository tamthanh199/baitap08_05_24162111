package vn.hcmute.springboot.controller;

import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hcmute.springboot.entity.Category;
import vn.hcmute.springboot.entity.Product;
import vn.hcmute.springboot.service.ICategoryService;
import vn.hcmute.springboot.service.IProductService;
import vn.hcmute.springboot.util.FileUploadUtil;

@Controller
public class AdminProductController {
    private final IProductService productService;
    private final ICategoryService categoryService;

    public AdminProductController(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/product/list")
    public String list(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/list-product";
    }

    @GetMapping("/admin/product/add")
    public String addPage(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/add-product";
    }

    @PostMapping("/admin/product/add")
    public String add(@RequestParam(defaultValue="") String name,
            @RequestParam(defaultValue="") String description,
            @RequestParam(defaultValue="") String price,
            @RequestParam(defaultValue="") String categoryId,
            @RequestParam(name="image", required=false) MultipartFile image,
            Model model) {
        name = name.trim(); description = description.trim(); price = price.trim(); categoryId = categoryId.trim();
        model.addAttribute("name", name); model.addAttribute("description", description);
        model.addAttribute("price", price); model.addAttribute("selectedCategoryId", categoryId);
        if (name.isEmpty() || price.isEmpty() || categoryId.isEmpty()) return addError(model, "Vui lòng nhập đầy đủ thông tin.");
        try {
            BigDecimal priceValue = new BigDecimal(price);
            int cateId = Integer.parseInt(categoryId);
            if (priceValue.compareTo(BigDecimal.ZERO) < 0) return addError(model, "Giá phải lớn hơn hoặc bằng 0.");
            Category category = categoryService.getById(cateId);
            if (category == null) return addError(model, "Danh mục không tồn tại.");
            Product product = new Product();
            product.setName(name); product.setDescription(description); product.setPrice(priceValue); product.setCategory(category);
            product.setImage(FileUploadUtil.saveProductImage(image));
            productService.insert(product);
            return "redirect:/admin/product/list";
        } catch (NumberFormatException e) {
            return addError(model, "Giá hoặc danh mục không hợp lệ.");
        } catch (IOException e) {
            return addError(model, e.getMessage());
        }
    }

    @GetMapping("/admin/product/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        Product product = productService.getById(id);
        if (product == null) return "redirect:/admin/product/list";
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "admin/edit-product";
    }

    @PostMapping("/admin/product/edit")
    public String edit(@RequestParam("id") int id,
            @RequestParam(defaultValue="") String name,
            @RequestParam(defaultValue="") String description,
            @RequestParam(defaultValue="") String price,
            @RequestParam(defaultValue="") String categoryId,
            @RequestParam(name="image", required=false) MultipartFile image,
            Model model) {
        Product product = productService.getById(id);
        if (product == null) return "redirect:/admin/product/list";
        name=name.trim(); description=description.trim(); price=price.trim(); categoryId=categoryId.trim();
        if (name.isEmpty() || price.isEmpty() || categoryId.isEmpty()) return editError(model, product, categoryId, "Vui lòng nhập đầy đủ thông tin.");
        try {
            BigDecimal priceValue = new BigDecimal(price);
            int cateId = Integer.parseInt(categoryId);
            if (priceValue.compareTo(BigDecimal.ZERO) < 0) return editError(model, product, categoryId, "Giá phải lớn hơn hoặc bằng 0.");
            Category category = categoryService.getById(cateId);
            if (category == null) return editError(model, product, categoryId, "Danh mục không tồn tại.");
            String oldImage = product.getImage();
            String newImage = FileUploadUtil.saveProductImage(image);
            product.setName(name); product.setDescription(description); product.setPrice(priceValue); product.setCategory(category);
            if (newImage != null) product.setImage(newImage);
            productService.update(product);
            if (newImage != null && oldImage != null && !oldImage.isBlank()) {
                try { FileUploadUtil.deleteProductImage(oldImage); } catch (IOException ignored) { }
            }
            return "redirect:/admin/product/list";
        } catch (NumberFormatException e) {
            return editError(model, product, categoryId, "Giá hoặc danh mục không hợp lệ.");
        } catch (IOException e) {
            return editError(model, product, categoryId, e.getMessage());
        }
    }

    @GetMapping("/admin/product/delete")
    public String delete(@RequestParam("id") int id) {
        Product product = productService.getById(id);
        if (product != null) {
            String image = product.getImage();
            productService.delete(id);
            if (image != null && !image.isBlank()) {
                try { FileUploadUtil.deleteProductImage(image); } catch (IOException ignored) { }
            }
        }
        return "redirect:/admin/product/list";
    }

    private String addError(Model model, String message) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("message", message);
        return "admin/add-product";
    }

    private String editError(Model model, Product product, String selectedCategoryId, String message) {
        model.addAttribute("product", product);
        model.addAttribute("selectedCategoryId", selectedCategoryId);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("message", message);
        return "admin/edit-product";
    }
}
