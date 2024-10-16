package com.project2.Project2.controller;

import com.project2.Project2.model.User;
import com.project2.Project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<User> assignRolesToUser(@PathVariable String id, @RequestBody String roles) {
        Optional<User> updatedUser = userService.assignRole(id, roles);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/roles")
    public ResponseEntity<User> removeRolesFromUser(@PathVariable String id, @RequestBody String roles) {
        Optional<User> updatedUser = userService.removeRole(id, roles);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<Set<String>> getUserRoles(@PathVariable String id) {
        Optional<Set<String>> roles = userService.getUserRoles(id);
        return roles.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}