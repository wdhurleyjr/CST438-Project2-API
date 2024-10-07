package com.project2.Project2.controller;

import com.project2.Project2.model.User;
import com.project2.Project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/wishlist/{bookId}")
    public ResponseEntity<User> addBookToWishlist(@PathVariable String id, @PathVariable String bookId) {
        Optional<User> updatedUser = userService.addBookToWishlist(id, bookId);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/wishlist/{bookId}")
    public ResponseEntity<User> removeBookFromWishlist(@PathVariable String id, @PathVariable String bookId) {
        Optional<User> updatedUser = userService.removeBookFromWishlist(id, bookId);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/wishlist")
    public ResponseEntity<List<String>> getUserWishlist(@PathVariable String id) {
        Optional<List<String>> wishlist = userService.getUserWishlist(id);
        return wishlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<User> assignRolesToUser(@PathVariable String id, @RequestBody Set<String> roles) {
        Optional<User> updatedUser = userService.assignRole(id, roles);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<User> removeRolesFromUser(@PathVariable String id, @RequestBody Set<String> roles) {
        Optional<User> updatedUser = userService.removeRole(id, roles);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<Set<String>> getUserRoles(@PathVariable String id) {
        Optional<Set<String>> roles = userService.getUserRoles(id);
        return roles.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

