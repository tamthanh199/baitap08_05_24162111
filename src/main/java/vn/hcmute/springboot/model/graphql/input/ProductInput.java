package vn.hcmute.springboot.model.graphql.input;

public record ProductInput(

    String productName,

    String description,

    Double price,

    String image,

    Integer categoryId

) {
}