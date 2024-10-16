package com.project2.Project2.controller;

import com.project2.Project2.model.AuthRequest;
import com.project2.Project2.model.AuthResponse;
import com.project2.Project2.model.User;
import com.project2.Project2.service.JwtService;
import com.project2.Project2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("testUser", "password");
        UserDetails userDetails = mock(UserDetails.class);

        // Mock the behavior for userService and jwtService
        when(userService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptySet());
        when(jwtService.generateToken("testUser", Collections.emptySet())).thenReturn("mockToken");

        // Mock successful authentication
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);  // Return null to simulate a successful authentication

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthResponse actualResponse = (AuthResponse) response.getBody();
        assertNotNull(actualResponse);
        assertEquals("mockToken", actualResponse.getToken());
    }


    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("testUser", "wrongPassword");
        UserDetails userDetails = mock(UserDetails.class);

        when(userService.loadUserByUsername("testUser")).thenReturn(userDetails);

        // Simulate authentication failure by throwing an exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }


    @Test
    void testLogin_InternalServerError() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("testUser", "password");

        // Simulate internal server error
        when(userService.loadUserByUsername("testUser")).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An internal server error occurred", response.getBody());
    }

    @Test
    void testRegister_Success() {
        // Arrange
        User user = new User();
        user.setUsername("newUser");

        User savedUser = new User();
        savedUser.setUsername("newUser");
        savedUser.setRoles(Collections.singleton("ROLE_USER"));

        // Mock user saving and token generation
        when(userService.saveUser(user)).thenReturn(savedUser);
        when(jwtService.generateToken("newUser", savedUser.getRoles())).thenReturn("mockToken");

        // Act
        ResponseEntity<?> response = authController.register(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Validate the response body
        assertNotNull(response.getBody(), "Response body should not be null");
        AuthResponse actualResponse = (AuthResponse) response.getBody();
        assertEquals("mockToken", actualResponse.getToken(), "Tokens should match");
    }

    @Test
    void testRegister_Failure() {
        // Arrange
        User user = new User();

        // Simulate failure during user saving
        when(userService.saveUser(user)).thenThrow(new AuthenticationServiceException("Error"));

        // Act
        ResponseEntity<?> response = authController.register(user);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while creating the account.", response.getBody());
    }
}

