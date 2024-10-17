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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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

        // Mock user details
        when(userService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        // Mock JWT token generation
        when(jwtService.generateToken("testUser", Collections.emptyList())).thenReturn("mockToken");

        // Simulate successful authentication
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

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

        // Simulate failed authentication by throwing an exception
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

        // Simulate an internal server error
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

        User savedUser = mock(User.class);
        when(savedUser.getUsername()).thenReturn("newUser");

        // Create a Collection without explicit type parameters
        Collection authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // Mock the behavior without strict type enforcement
        when(savedUser.getAuthorities()).thenReturn(authorities);
        when(userService.saveUser(user)).thenReturn(savedUser);
        when(jwtService.generateToken("newUser", authorities)).thenReturn("mockToken");

        // Act
        ResponseEntity<?> response = authController.register(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthResponse actualResponse = (AuthResponse) response.getBody();
        assertNotNull(actualResponse, "Response body should not be null");
        assertEquals("mockToken", actualResponse.getToken(), "Token should match expected value");
    }






    @Test
    void testRegister_Failure() {
        // Arrange
        User user = new User();

        // Simulate an exception when saving the user
        when(userService.saveUser(user)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = authController.register(user);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while creating the account.", response.getBody());
    }
}
