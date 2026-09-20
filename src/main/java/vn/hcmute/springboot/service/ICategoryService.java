package vn.hcmute.springboot.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.hcmute.springboot.entity.Category;

public interface ICategoryService {
    List<Category> findAll();
    List<Category> getAll();
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByCategorynameContaining(String keyword, Pageable pageable);
    Optional<Category> findById(int id);
    Category getById(int id);
    Category save(Category category);
    void insert(Category category);
    void update(Category category);
    void deleteById(int id);
    void delete(int id);
    long count();
}
