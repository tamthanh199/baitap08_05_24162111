package vn.hcmute.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphQLPageController {

    @GetMapping("/graphql-ui/home")
    public String home() {

        return "graphql/home";
    }


    @GetMapping("/admin/graphql/categories")
    public String categoryAdmin() {

        return "graphql/categories";
    }


    @GetMapping("/admin/graphql/products")
    public String productAdmin() {

        return "graphql/products";
    }
}