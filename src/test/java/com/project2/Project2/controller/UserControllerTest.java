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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        User user1 = new User("user1", "password1", "user1@example.com", "First1", "Last1", roles);
        User user2 = new User("user2", "password2", "user2@example.com", "First2", "Last2", roles);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void findUserById_ShouldReturnUser_WhenFound() {
        // Arrange
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1", roles);
        when(userService.getUserById("1")).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.findUserById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById("1");
    }

    @Test
    void findUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.getUserById("1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.findUserById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById("1");
    }

    @Test
    void saveUser_ShouldReturnCreatedUser() {
        // Arrange
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1", roles);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.saveUser(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenFound() {
        // Arrange
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        User updatedUser = new User("user1", "updatedPassword", "user1@example.com", "First1", "Last1", roles);
        when(userService.updateUser(eq("1"), any(User.class))).thenReturn(Optional.of(updatedUser));

        // Act
        ResponseEntity<User> response = userController.updateUser("1", updatedUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUser(eq("1"), any(User.class));
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.updateUser(eq("1"), any(User.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.updateUser("1", new User());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(eq("1"), any(User.class));
    }

    @Test
    void deleteUserById_ShouldReturnNoContent_WhenUserIsDeleted() {
        // Arrange
        when(userService.deleteUserById("1")).thenReturn(true);

        // Act
        ResponseEntity<Void> response = userController.deleteUserById("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUserById("1");
    }

    @Test
    void deleteUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.deleteUserById("1")).thenReturn(false);

        // Act
        ResponseEntity<Void> response = userController.deleteUserById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUserById("1");
    }
}
