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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void findUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        User user = new User("user1", "password", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
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
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        // Arrange
        User updatedUser = new User("user1", "newPassword", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        when(userService.updateUser("1", updatedUser)).thenReturn(Optional.of(updatedUser));

        // Act
        ResponseEntity<User> response = userController.updateUser("1", updatedUser);

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
        ResponseEntity<User> response = userController.updateUser("1", updatedUser);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser("1", updatedUser);
    }

    @Test
    void deleteUserById_ShouldReturnNoContent_WhenUserExists() {
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

    @Test
    void addBookToWishlist_ShouldReturnUpdatedUser_WhenSuccessful() {
        // Arrange
        User user = new User("user1", "password", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        user.addToWishlist("book1");
        when(userService.addBookToWishlist("1", "book1")).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.addBookToWishlist("1", "book1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).addBookToWishlist("1", "book1");
    }

    @Test
    void addBookToWishlist_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.addBookToWishlist("1", "book1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.addBookToWishlist("1", "book1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).addBookToWishlist("1", "book1");
    }

    @Test
    void removeBookFromWishlist_ShouldReturnUpdatedUser_WhenSuccessful() {
        // Arrange
        User user = new User("user1", "password", "user1@example.com", "First1", "Last1", List.of("ROLE_USER"));
        user.addToWishlist("book1");
        user.removeFromWishlist("book1");
        when(userService.removeBookFromWishlist("1", "book1")).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.removeBookFromWishlist("1", "book1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).removeBookFromWishlist("1", "book1");
    }

    @Test
    void removeBookFromWishlist_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.removeBookFromWishlist("1", "book1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.removeBookFromWishlist("1", "book1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).removeBookFromWishlist("1", "book1");
    }

    @Test
    void getUserWishlist_ShouldReturnWishlist_WhenSuccessful() {
        // Arrange
        List<String> wishlist = List.of("book1", "book2");
        when(userService.getUserWishlist("1")).thenReturn(Optional.of(wishlist));

        // Act
        ResponseEntity<List<String>> response = userController.getUserWishlist("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wishlist, response.getBody());
        verify(userService, times(1)).getUserWishlist("1");
    }

    @Test
    void getUserWishlist_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        when(userService.getUserWishlist("1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<List<String>> response = userController.getUserWishlist("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserWishlist("1");
    }
}

