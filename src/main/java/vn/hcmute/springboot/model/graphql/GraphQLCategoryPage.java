package vn.hcmute.springboot.model.graphql;

import java.util.List;

import org.springframework.data.domain.Page;

import vn.hcmute.springboot.entity.Category;

public class GraphQLCategoryPage {

    private List<GraphQLCategoryModel>
        content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;


    public GraphQLCategoryPage() {
    }


    public static GraphQLCategoryPage
            fromPage(
                Page<Category>
                    categoryPage) {

        GraphQLCategoryPage result =
            new GraphQLCategoryPage();


        result.setContent(

            categoryPage
                .getContent()
                .stream()
                .map(
                    GraphQLCategoryModel
                        ::fromEntity
                )
                .toList()
        );


        result.setPage(
            categoryPage.getNumber()
        );


        result.setSize(
            categoryPage.getSize()
        );


        result.setTotalElements(
            categoryPage
                .getTotalElements()
        );


        result.setTotalPages(
            categoryPage
                .getTotalPages()
        );


        result.setFirst(
            categoryPage.isFirst()
        );


        result.setLast(
            categoryPage.isLast()
        );


        return result;
    }


    public List<GraphQLCategoryModel>
            getContent() {

        return content;
    }


    public void setContent(
            List<GraphQLCategoryModel>
                content) {

        this.content =
            content;
    }


    public int getPage() {
        return page;
    }


    public void setPage(
            int page) {

        this.page =
            page;
    }


    public int getSize() {
        return size;
    }


    public void setSize(
            int size) {

        this.size =
            size;
    }


    public long getTotalElements() {
        return totalElements;
    }


    public void setTotalElements(
            long totalElements) {

        this.totalElements =
            totalElements;
    }


    public int getTotalPages() {
        return totalPages;
    }


    public void setTotalPages(
            int totalPages) {

        this.totalPages =
            totalPages;
    }


    public boolean isFirst() {
        return first;
    }


    public void setFirst(
            boolean first) {

        this.first =
            first;
    }


    public boolean isLast() {
        return last;
    }


    public void setLast(
            boolean last) {

        this.last =
            last;
    }
}