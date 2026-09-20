package vn.hcmute.springboot.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.hcmute.springboot.entity.Product;
import vn.hcmute.springboot.repository.IProductRepository;
import vn.hcmute.springboot.service.IProductService;

@Service
public class ProductService
        implements IProductService {

    private final IProductRepository
        productRepository;


    public ProductService(
            IProductRepository
                productRepository) {

        this.productRepository =
            productRepository;
    }


    @Override
    public List<Product> getAll() {

        return productRepository.findAll(

            Sort.by(
                Sort.Direction.DESC,
                "createdAt",
                "id"
            )
        );
    }


    @Override
    public List<Product> getLatest(
            int limit) {

        Pageable pageable =
            PageRequest.of(
                0,
                Math.max(1, limit),

                Sort.by(
                    Sort.Direction.DESC,
                    "createdAt",
                    "id"
                )
            );

        return productRepository
            .findAll(pageable)
            .getContent();
    }


    @Override
    public List<Product> getPage(
            int page,
            int pageSize) {

        Pageable pageable =
            PageRequest.of(

                Math.max(
                    0,
                    page - 1
                ),

                pageSize,

                Sort.by(
                    Sort.Direction.DESC,
                    "createdAt",
                    "id"
                )
            );

        return productRepository
            .findAll(pageable)
            .getContent();
    }


    @Override
    public Page<Product> getPage(
            Pageable pageable) {

        return productRepository
            .findAll(pageable);
    }


    @Override
    public long count() {

        return productRepository.count();
    }


    @Override
    public Product getById(
            int id) {

        return productRepository
            .findById(id)
            .orElse(null);
    }


    @Override
    public Product save(
            Product product) {

        return productRepository
            .save(product);
    }


    @Override
    public void insert(
            Product product) {

        productRepository
            .save(product);
    }


    @Override
    public void update(
            Product product) {

        productRepository
            .save(product);
    }


    @Override
    public void delete(
            int id) {

        productRepository
            .deleteById(id);
    }

    @Override
    public List<Product>
            getAllByPriceAsc() {

        return productRepository
            .findAllByOrderByPriceAscIdAsc();
    }


    @Override
    public List<Product>
            getByCategoryId(
                int categoryId) {

        return productRepository
            .findByCategoryCategoryIdOrderByPriceAscIdAsc(
                categoryId
            );
    }


    @Override
    public Page<Product>
            searchByName(
                String keyword,
                Pageable pageable) {

        return productRepository
            .findByNameContainingIgnoreCase(
                keyword,
                pageable
            );
    }
}