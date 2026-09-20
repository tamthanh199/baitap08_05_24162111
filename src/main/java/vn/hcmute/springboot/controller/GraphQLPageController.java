package vn.hcmute.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphQLPageController {

    @GetMapping("/graphql-ui/home")
    public String home() {

        return "graphql/home";
    }
}