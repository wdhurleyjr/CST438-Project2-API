package com.project2.Project2.controller;

import com.project2.Project2.model.User;
import com.project2.Project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        boolean deleted = userService.deleteUserById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a book to the user's wishlist
    @PostMapping("/{id}/wishlist/{bookId}")
    public ResponseEntity<User> addBookToWishlist(@PathVariable String id, @PathVariable String bookId) {
        Optional<User> updatedUser = userService.addBookToWishlist(id, bookId);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Remove a book from the user's wishlist
    @DeleteMapping("/{id}/wishlist/{bookId}")
    public ResponseEntity<User> removeBookFromWishlist(@PathVariable String id, @PathVariable String bookId) {
        Optional<User> updatedUser = userService.removeBookFromWishlist(id, bookId);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get the user's wishlist
    @GetMapping("/{id}/wishlist")
    public ResponseEntity<List<String>> getUserWishlist(@PathVariable String id) {
        Optional<List<String>> wishlist = userService.getUserWishlist(id);
        return wishlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
