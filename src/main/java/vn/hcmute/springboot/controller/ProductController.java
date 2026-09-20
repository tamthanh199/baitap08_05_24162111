package vn.hcmute.springboot.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hcmute.springboot.entity.Product;
import vn.hcmute.springboot.service.IProductService;

@Controller
public class ProductController {
    private final IProductService productService;
    public ProductController(IProductService productService) { this.productService = productService; }

    @GetMapping("/product")
    public String list(@RequestParam(name="page", defaultValue="1") int page, Model model) {
        int pageSize = 6;
        int currentPage = Math.max(1, page);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Product> result = productService.getPage(pageable);
        model.addAttribute("products", result.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalItems", result.getTotalElements());
        return "product";
    }

    @GetMapping("/product/detail")
    public String detail(@RequestParam("id") int id, Model model) {
        Product product = productService.getById(id);
        if (product == null) return "redirect:/product";
        model.addAttribute("product", product);
        return "product-detail";
    }
}
