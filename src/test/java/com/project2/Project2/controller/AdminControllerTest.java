package com.project2.Project2.controller;

import com.project2.Project2.model.User;
import com.project2.Project2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnCreatedUser_WhenSuccessful() {
        // Arrange
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        when(userService.saveUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = adminController.createUser(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void createUser_ShouldReturnNotFound_WhenSaveFails() {
        // Arrange
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        when(userService.saveUser(user)).thenReturn(null);

        // Act
        ResponseEntity<User> response = adminController.createUser(user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        // Arrange
        User updatedUser = new User("user1", "newPassword", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        when(userService.updateUser("1", updatedUser)).thenReturn(Optional.of(updatedUser));

        // Act
        ResponseEntity<User> response = adminController.updateUser("1", updatedUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUser("1", updatedUser);
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        User updatedUser = new User("user1", "newPassword", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        when(userService.updateUser("1", updatedUser)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = adminController.updateUser("1", updatedUser);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser("1", updatedUser);
    }

    @Test
    void assignRoleToUser_ShouldReturnUpdatedUser_WhenSuccessful() {
        // Arrange
        User updatedUser = new User("user1", "password1", "user1@example.com",
                "First1", "Last1", List.of("ROLE_ADMIN"));
        when(userService.assignRole("1", "ROLE_ADMIN")).thenReturn(Optional.of(updatedUser));

        // Act
        ResponseEntity<User> response = adminController.assignRoleToUser("1", "ROLE_ADMIN");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).assignRole("1", "ROLE_ADMIN");
    }

    @Test
    void assignRoleToUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.assignRole("1", "ROLE_ADMIN")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = adminController.assignRoleToUser("1", "ROLE_ADMIN");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).assignRole("1", "ROLE_ADMIN");
    }

    @Test
    void removeRoleFromUser_ShouldReturnUpdatedUser_WhenSuccessful() {
        // Arrange
        User updatedUser = new User("user1", "password1", "user1@example.com",
                "First1", "Last1", List.of());
        when(userService.removeRole("1", "ROLE_USER")).thenReturn(Optional.of(updatedUser));

        // Act
        ResponseEntity<User> response = adminController.removeRoleFromUser("1", "ROLE_USER");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).removeRole("1", "ROLE_USER");
    }

    @Test
    void removeRoleFromUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.removeRole("1", "ROLE_USER")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = adminController.removeRoleFromUser("1", "ROLE_USER");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).removeRole("1", "ROLE_USER");
    }


    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        User user1 = new User("user1", "password1", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        User user2 = new User("user2", "password2", "user2@example.com", "First2", "Last2", List.of("ROLE_ADMIN"));
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        // Act
        ResponseEntity<List<User>> response = adminController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(user1, user2), response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserRoles_ShouldReturnRoles_WhenUserExists() {
        // Arrange
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");
        when(userService.getUserRoles("1")).thenReturn(Optional.of(roles));

        // Act
        ResponseEntity<List<String>> response = adminController.getUserRoles("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
        verify(userService, times(1)).getUserRoles("1");
    }

    @Test
    void getUserRoles_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.getUserRoles("1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<String>> response = adminController.getUserRoles("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserRoles("1");
    }
}

