package vn.hcmute.springboot.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.hcmute.springboot.entity.Product;

public interface IProductService {
    List<Product> getAll();
    List<Product> getLatest(int limit);
    List<Product> getPage(int page, int pageSize);
    Page<Product> getPage(Pageable pageable);
    long count();
    Product getById(int id);
    Product save(Product product);
    void insert(Product product);
    void update(Product product);
    void delete(int id);
}
