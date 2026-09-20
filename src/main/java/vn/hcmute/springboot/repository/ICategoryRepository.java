package vn.hcmute.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hcmute.springboot.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByCategorynameContainingIgnoreCase(String keyword, Pageable pageable);
}
