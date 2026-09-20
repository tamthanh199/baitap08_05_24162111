package vn.hcmute.springboot.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.hcmute.springboot.entity.User;

public interface IUserService {
    boolean register(User user);
    User login(String username, String password);
    User getById(int id);
    Optional<User> findById(int id);
    User getByUsername(String username);
    User getByEmail(String email);
    User save(User user);
    void update(User user);
    void deleteById(int id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean usernameExists(String username, Integer ignoreUserId);
    boolean emailExists(String email, Integer ignoreUserId);
    Page<User> findAll(Pageable pageable);
    Page<User> search(String keyword, Pageable pageable);
    long count();
}
