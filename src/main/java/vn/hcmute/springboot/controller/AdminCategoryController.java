package vn.hcmute.springboot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.hcmute.springboot.entity.Category;
import vn.hcmute.springboot.model.CategoryModel;
import vn.hcmute.springboot.service.ICategoryService;
import vn.hcmute.springboot.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final ICategoryService categoryService;

    public AdminCategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/", "/searchpaginated"})
    public String list(ModelMap model,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "name", required = false) String legacyName,
            @RequestParam(name = "page") Optional<Integer> page,
            @RequestParam(name = "size") Optional<Integer> size) {

        if (!StringUtils.hasText(keyword) && StringUtils.hasText(legacyName)) keyword = legacyName;
        int currentPage = Math.max(page.orElse(1), 1);
        int pageSize = normalizePageSize(size.orElse(5));
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryId").descending());

        Page<Category> resultPage;
        if (StringUtils.hasText(keyword)) {
            keyword = keyword.trim();
            resultPage = categoryService.findByCategorynameContaining(keyword, pageable);
        } else {
            keyword = "";
            resultPage = categoryService.findAll(pageable);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("name", keyword);
        model.addAttribute("categoryPage", resultPage);
        model.addAttribute("categories", resultPage.getContent());
        addPageNumbers(model, resultPage);
        return "admin/categories/list";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIsEdit(false);
        categoryModel.setStatus(1);
        model.addAttribute("category", categoryModel);
        return "admin/categories/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
            @Valid @ModelAttribute("category") CategoryModel categoryModel,
            BindingResult result,
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) return new ModelAndView("admin/categories/addOrEdit", model);

        Category entity;
        String oldImage = null;
        if (categoryModel.getIsEdit()) {
            entity = categoryService.findById(categoryModel.getCategoryId()).orElse(null);
            if (entity == null) {
                redirectAttributes.addFlashAttribute("message", "Category không tồn tại!");
                return new ModelAndView("redirect:/admin/categories");
            }
            oldImage = entity.getImages();
        } else {
            entity = new Category();
        }

        BeanUtils.copyProperties(categoryModel, entity, "images", "isEdit");
        entity.setCategoryname(categoryModel.getCategoryname().trim());

        try {
            String newImage = FileUploadUtil.saveCategoryImage(imageFile);
            if (newImage != null) entity.setImages(newImage);
            else if (!categoryModel.getIsEdit()) entity.setImages(categoryModel.getImages());
            categoryService.save(entity);

            if (newImage != null && oldImage != null && !oldImage.isBlank()) {
                try { FileUploadUtil.deleteCategoryImage(oldImage); } catch (IOException ignored) { }
            }
        } catch (IOException e) {
            model.addAttribute("message", e.getMessage());
            return new ModelAndView("admin/categories/addOrEdit", model);
        }

        redirectAttributes.addFlashAttribute("message",
                categoryModel.getIsEdit() ? "Category is Edited!!!!!!!!" : "Category is saved!!!!!!!!");
        return new ModelAndView("redirect:/admin/categories");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<Category> optional = categoryService.findById(id);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Category is not existed!!!!");
            return new ModelAndView("redirect:/admin/categories");
        }
        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(optional.get(), categoryModel);
        categoryModel.setIsEdit(true);
        model.addAttribute("category", categoryModel);
        return new ModelAndView("admin/categories/addOrEdit", model);
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(ModelMap model, @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<Category> optional = categoryService.findById(id);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Category không tồn tại!");
            return new ModelAndView("redirect:/admin/categories");
        }
        model.addAttribute("category", optional.get());
        return new ModelAndView("admin/categories/view", model);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.getById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("message", "Category không tồn tại!");
            return new ModelAndView("redirect:/admin/categories");
        }
        try {
            categoryService.deleteById(id);
            if (category.getImages() != null && !category.getImages().isBlank()) {
                try { FileUploadUtil.deleteCategoryImage(category.getImages()); } catch (IOException ignored) { }
            }
            redirectAttributes.addFlashAttribute("message", "Category is deleted!!!!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Không thể xóa Category đang được Product sử dụng.");
        }
        return new ModelAndView("redirect:/admin/categories");
    }

    private int normalizePageSize(int size) {
        return switch (size) { case 3, 5, 10, 15, 20 -> size; default -> 5; };
    }

    private void addPageNumbers(ModelMap model, Page<?> resultPage) {
        int totalPages = resultPage.getTotalPages();
        if (totalPages <= 0) { model.addAttribute("pageNumbers", List.of()); return; }
        int currentPage = resultPage.getNumber() + 1;
        int start = Math.max(1, currentPage - 2);
        int end = Math.min(currentPage + 2, totalPages);
        List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
    }
}
