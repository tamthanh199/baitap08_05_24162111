package vn.hcmute.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LegacyCategoryRedirectController {
    @GetMapping("/admin/category/list") public String list() { return "redirect:/admin/categories"; }
    @GetMapping("/admin/category/add") public String add() { return "redirect:/admin/categories/add"; }
    @GetMapping("/admin/category/edit") public String edit(@RequestParam("id") int id) { return "redirect:/admin/categories/edit/" + id; }
    @GetMapping("/admin/category/delete") public String delete(@RequestParam("id") int id) { return "redirect:/admin/categories/delete/" + id; }
}
