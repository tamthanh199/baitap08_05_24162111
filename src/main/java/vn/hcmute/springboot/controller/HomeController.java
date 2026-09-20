package vn.hcmute.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.hcmute.springboot.service.IProductService;

@Controller
public class HomeController {
    private final IProductService productService;

    public HomeController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("latestProducts", productService.getLatest(10));
        return "index";
    }
}
