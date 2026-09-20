package vn.hcmute.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hcmute.springboot.entity.Product;

@Repository
public interface IProductRepository
        extends JpaRepository<Product, Integer> {

    List<Product>
        findTop10ByOrderByCreatedAtDescIdDesc();

    Page<Product>
        findAllByOrderByCreatedAtDescIdDesc(
            Pageable pageable
        );

    List<Product>
        findAllByOrderByPriceAscIdAsc();

    List<Product>
        findByCategoryCategoryIdOrderByPriceAscIdAsc(
            int categoryId
        );

    Page<Product>
        findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
        );
}