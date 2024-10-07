package com.project2.Project2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.project2.Project2.model.User;
import com.project2.Project2.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        // Hash the password using BCryptPasswordEncoder
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Set default role if not present
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new HashSet<>(Collections.singletonList("USER")));
        }

        return userRepository.save(user);
    }

    public Optional<User> updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            // Hash the updated password, if present
            if (updatedUser.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
                user.setPassword(hashedPassword);
            }
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            return userRepository.save(user);
        });
    }

    public boolean deleteUserById(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Optional<User> addBookToWishlist(String userId, String bookId) {
        return userRepository.findById(userId).map(user -> {
            user.addToWishlist(bookId);
            return userRepository.save(user);
        });
    }

    public Optional<User> removeBookFromWishlist(String userId, String bookId) {
        return userRepository.findById(userId).map(user -> {
            user.removeFromWishlist(bookId);
            return userRepository.save(user);
        });
    }

    public Optional<List<String>> getUserWishlist(String userId) {
        return userRepository.findById(userId).map(User::getWishlist);
    }

    public Optional<User> assignRole(String userId, String role) {
        return userRepository.findById(userId).map(user -> {
            user.getRoles().add(role);
            return userRepository.save(user);
        });
    }

    public Optional<User> removeRole(String userId, String role) {
        return userRepository.findById(userId).map(user -> {
            user.getRoles().remove(role);
            return userRepository.save(user);
        });
    }

    public Optional<Set<String>> getUserRoles(String userId) {
        return userRepository.findById(userId).map(User::getRoles);
    }
}

