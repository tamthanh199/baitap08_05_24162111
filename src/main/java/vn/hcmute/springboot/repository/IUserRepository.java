package vn.hcmute.springboot.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hcmute.springboot.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
    boolean existsByUsernameIgnoreCaseAndUserIdNot(String username, int userId);
    boolean existsByEmailIgnoreCaseAndUserIdNot(String email, int userId);
    Page<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String fullName, String email, Pageable pageable);
}
