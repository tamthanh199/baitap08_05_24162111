package vn.hcmute.springboot.service.impl;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hcmute.springboot.entity.User;
import vn.hcmute.springboot.repository.IUserRepository;
import vn.hcmute.springboot.service.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(User user) {
        if (existsByUsername(user.getUsername()) || existsByEmail(user.getEmail())
                || (user.getPhone() != null && !user.getPhone().isBlank() && existsByPhone(user.getPhone()))) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public User login(String username, String password) {
        User user = getByUsername(username);
        return user != null && password != null && password.equals(user.getPassword()) ? user : null;
    }

    @Override public User getById(int id) { return userRepository.findById(id).orElse(null); }
    @Override public Optional<User> findById(int id) { return userRepository.findById(id); }
    @Override public User getByUsername(String username) { return userRepository.findByUsernameIgnoreCase(username).orElse(null); }
    @Override public User getByEmail(String email) { return userRepository.findByEmailIgnoreCase(email).orElse(null); }
    @Override public User save(User user) { return userRepository.save(user); }
    @Override public void update(User user) { userRepository.save(user); }
    @Override public void deleteById(int id) { userRepository.deleteById(id); }
    @Override public boolean existsByUsername(String username) { return userRepository.existsByUsernameIgnoreCase(username); }
    @Override public boolean existsByEmail(String email) { return userRepository.existsByEmailIgnoreCase(email); }
    @Override public boolean existsByPhone(String phone) { return userRepository.existsByPhone(phone); }
    @Override public boolean usernameExists(String username, Integer ignoreUserId) {
        return ignoreUserId == null || ignoreUserId <= 0
                ? userRepository.existsByUsernameIgnoreCase(username)
                : userRepository.existsByUsernameIgnoreCaseAndUserIdNot(username, ignoreUserId);
    }
    @Override public boolean emailExists(String email, Integer ignoreUserId) {
        return ignoreUserId == null || ignoreUserId <= 0
                ? userRepository.existsByEmailIgnoreCase(email)
                : userRepository.existsByEmailIgnoreCaseAndUserIdNot(email, ignoreUserId);
    }
    @Override public Page<User> findAll(Pageable pageable) { return userRepository.findAll(pageable); }
    @Override public Page<User> search(String keyword, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword, keyword, keyword, pageable);
    }
    @Override public long count() { return userRepository.count(); }
}
