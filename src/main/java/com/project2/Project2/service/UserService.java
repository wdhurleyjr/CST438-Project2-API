package com.project2.Project2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project2.Project2.model.User;
import com.project2.Project2.repository.UserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to find a user by username
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Method to find a user by email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Method to get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Method to get a user by ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // Method to save a new user
    public User saveUser(User user) {
        // Hash the password before saving using standalone bcrypt
        String hashedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    // Method to update an existing user
    public Optional<User> updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            // Hash the password if it's updated
            if (updatedUser.getPassword() != null) {
                String hashedPassword = BCrypt.withDefaults().hashToString(12, updatedUser.getPassword().toCharArray());
                user.setPassword(hashedPassword);
            }
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            return userRepository.save(user);
        });
    }

    // Method to delete a user by ID
    public boolean deleteUserById(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Method to delete a user
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    // New method to add a book to the user's wishlist
    public Optional<User> addBookToWishlist(String userId, String bookId) {
        return userRepository.findById(userId).map(user -> {
            user.addToWishlist(bookId);  // Add the bookId to the wishlist
            return userRepository.save(user);
        });
    }

    // New method to remove a book from the user's wishlist
    public Optional<User> removeBookFromWishlist(String userId, String bookId) {
        return userRepository.findById(userId).map(user -> {
            user.removeFromWishlist(bookId);  // Remove the bookId from the wishlist
            return userRepository.save(user);
        });
    }

    // New method to get the user's wishlist
    public Optional<List<String>> getUserWishlist(String userId) {
        return userRepository.findById(userId).map(User::getWishlist);
    }
}
