package vn.hcmute.springboot.controller.graphql;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphQLTestController {

    @QueryMapping
    public String hello() {

        return "GraphQL mục 5 đã chạy thành công!";
    }
}