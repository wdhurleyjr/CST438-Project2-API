package com.project2.Project2.controller;

import com.project2.Project2.model.Book;
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
        User user = new User();
        user.setId("1");
        user.setUsername("user1");
        user.setPassword("password");
        user.setEmail("user1@example.com");
        user.setFirstName("First1");
        user.setLastName("Last1");
        user.setRoles(List.of("ROLE_USER"));

        when(userService.getUserById("1")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.findUserById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById("1");
    }

    @Test
    void findUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.getUserById("1")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.findUserById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById("1");
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        User updatedUser = new User();
        updatedUser.setId("1");
        updatedUser.setUsername("user1");
        updatedUser.setPassword("newPassword");
        updatedUser.setEmail("user1@example.com");
        updatedUser.setFirstName("First1");
        updatedUser.setLastName("Last1");
        updatedUser.setRoles(List.of("ROLE_USER"));

        when(userService.updateUser("1", updatedUser)).thenReturn(Optional.of(updatedUser));

        ResponseEntity<User> response = userController.updateUser("1", updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).updateUser("1", updatedUser);
    }

    @Test
    void deleteUserById_ShouldReturnNoContent_WhenUserExists() {
        when(userService.deleteUserById("1")).thenReturn(true);

        ResponseEntity<Void> response = userController.deleteUserById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUserById("1");
    }

    @Test
    void addBookToWishlist_ShouldReturnUpdatedUser_WhenSuccessful() {
        User user = new User();
        user.setId("1");
        user.setUsername("user1");
        user.setPassword("password");
        user.setEmail("user1@example.com");
        user.setFirstName("First1");
        user.setLastName("Last1");
        user.setRoles(List.of("ROLE_USER"));

        when(userService.addBookToWishlist("user1", "book1")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.addBookToWishlist("user1", "book1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).addBookToWishlist("user1", "book1");
    }

    @Test
    void getUserWishlist_ShouldReturnWishlist_WhenSuccessful() {
        Book book1 = new Book();
        book1.setId("book1");

        Book book2 = new Book();
        book2.setId("book2");

        List<Book> wishlist = List.of(book1, book2);

        when(userService.getUserWishlist("user1")).thenReturn(Optional.of(wishlist));

        ResponseEntity<List<Book>> response = userController.getUserWishlist("user1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wishlist, response.getBody());
        verify(userService, times(1)).getUserWishlist("user1");
    }
}
