package vn.hcmute.springboot.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hcmute.springboot.entity.Category;
import vn.hcmute.springboot.repository.ICategoryRepository;
import vn.hcmute.springboot.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override public List<Category> findAll() { return categoryRepository.findAll(); }
    @Override public List<Category> getAll() { return categoryRepository.findAll(); }
    @Override public Page<Category> findAll(Pageable pageable) { return categoryRepository.findAll(pageable); }
    @Override public Page<Category> findByCategorynameContaining(String keyword, Pageable pageable) {
        return categoryRepository.findByCategorynameContainingIgnoreCase(keyword, pageable);
    }
    @Override public Optional<Category> findById(int id) { return categoryRepository.findById(id); }
    @Override public Category getById(int id) { return categoryRepository.findById(id).orElse(null); }
    @Override public Category save(Category category) { return categoryRepository.save(category); }
    @Override public void insert(Category category) { categoryRepository.save(category); }
    @Override public void update(Category category) { categoryRepository.save(category); }
    @Override public void deleteById(int id) { categoryRepository.deleteById(id); }
    @Override public void delete(int id) { categoryRepository.deleteById(id); }
    @Override public long count() { return categoryRepository.count(); }
}
