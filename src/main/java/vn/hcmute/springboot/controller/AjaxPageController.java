package vn.hcmute.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AjaxPageController {

    @GetMapping("/admin/ajax/categories")
    public String categoryAjaxPage() {

        return "admin/ajax/category";
    }

    @GetMapping("/admin/ajax/products")
    public String productAjaxPage() {

        return "admin/ajax/product";
    }
}