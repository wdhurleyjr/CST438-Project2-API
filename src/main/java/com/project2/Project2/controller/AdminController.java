package com.project2.Project2.controller;

import com.project2.Project2.model.User;
import com.project2.Project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Optional<User> createdUser = Optional.ofNullable(userService.saveUser(user));
        return createdUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<User> assignRolesToUser(@PathVariable String id, @RequestBody List<String> roles) {
        // Assign multiple roles by looping over the list
        Optional<User> updatedUser = roles.stream()
                .map(role -> userService.assignRole(id, role))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<User> removeRolesFromUser(@PathVariable String id, @RequestBody List<String> roles) {
        // Remove multiple roles by looping over the list
        Optional<User> updatedUser = roles.stream()
                .map(role -> userService.removeRole(id, role))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable String id) {
        Optional<List<String>> roles = userService.getUserRoles(id).map(List::copyOf);
        return roles.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
