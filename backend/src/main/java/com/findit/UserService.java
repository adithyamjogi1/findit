package com.findit;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "An account with this email already exists.");
        }

        User user = new User(
                name.trim(),
                email.trim().toLowerCase(),
                passwordEncoder.encode(password)
        );

        return userRepository.save(user);
    }

    public boolean loginUser(String email, String password) {
        return authenticateUser(email, password).isPresent();
    }

    public java.util.Optional<User> authenticateUser(String email, String password) {
        return userRepository.findByEmail(email.trim().toLowerCase())
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Transactional
    public boolean resetPassword(String email, String newPassword) {
        return userRepository.findByEmail(email.trim().toLowerCase())
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}
