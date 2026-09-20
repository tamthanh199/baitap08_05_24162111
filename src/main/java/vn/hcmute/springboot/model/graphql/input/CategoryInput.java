package vn.hcmute.springboot.model.graphql.input;

public record CategoryInput(

    String categoryName,

    String icon,

    Integer status

) {
}