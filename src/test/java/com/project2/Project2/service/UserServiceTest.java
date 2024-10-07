package com.project2.Project2.service;

import com.project2.Project2.model.User;
import com.project2.Project2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserByUsername_ShouldReturnUser_WhenFound() {
        // Arrange
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findUserByUsername("user1");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("user1", foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername("user1");
    }

    @Test
    void findUserByUsername_ShouldReturnEmpty_WhenNotFound() {
        // Arrange
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.findUserByUsername("user1");

        // Assert
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByUsername("user1");
    }

    @Test
    void findUserByEmail_ShouldReturnUser_WhenFound() {
        // Arrange
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1");
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findUserByEmail("user1@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("user1@example.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail("user1@example.com");
    }

    @Test
    void findUserByEmail_ShouldReturnEmpty_WhenNotFound() {
        // Arrange
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.findUserByEmail("user1@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail("user1@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        User user1 = new User("user1", "password1", "user1@example.com", "First1", "Last1");
        User user2 = new User("user2", "password2", "user2@example.com", "First2", "Last2");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenFound() {
        // Arrange
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.getUserById("1");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("user1", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void getUserById_ShouldReturnEmpty_WhenNotFound() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.getUserById("1");

        // Assert
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void saveUser_ShouldHashPasswordAndReturnSavedUser() {
        // Arrange
        User user = new User("user1", "password1", "user1@example.com", "First1", "Last1");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.saveUser(user);

        // Assert
        assertNotEquals("password1", savedUser.getPassword()); // Ensure the password is hashed
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateExistingUser_WhenFound() {
        // Arrange
        User existingUser = new User("user1", "password1", "user1@example.com", "First1", "Last1");
        User updatedUser = new User("user1", "newpassword", "user1@example.com", "First1", "Last1");
        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        Optional<User> result = userService.updateUser("1", updatedUser);

        // Assert
        assertTrue(result.isPresent());
        assertNotEquals("password1", result.get().getPassword()); // Ensure password is updated
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldReturnEmpty_WhenUserNotFound() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.updateUser("1", new User());

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(0)).save(any(User.class)); // Ensure save is not called
    }

    @Test
    void deleteUserById_ShouldReturnTrue_WhenUserExists() {
        // Arrange
        when(userRepository.existsById("1")).thenReturn(true);

        // Act
        boolean result = userService.deleteUserById("1");

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteUserById_ShouldReturnFalse_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.existsById("1")).thenReturn(false);

        // Act
        boolean result = userService.deleteUserById("1");

        // Assert
        assertFalse(result);
        verify(userRepository, times(0)).deleteById("1");
    }
}
