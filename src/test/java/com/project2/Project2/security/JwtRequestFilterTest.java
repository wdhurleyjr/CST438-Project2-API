package com.project2.Project2.security;

import com.project2.Project2.service.JwtService;
import com.project2.Project2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.servlet.FilterChain;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    private UserDetails mockUserDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();

        // Set up mock UserDetails object
        mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("testUser");
        when(mockUserDetails.getAuthorities()).thenReturn(List.of());
    }

    @Test
    void doFilterInternal_ShouldSetAuthentication_WhenJwtIsValid() throws Exception {
        // Arrange
        String jwt = "valid-jwt-token";
        String username = "testUser";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwt);
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mock the behavior of services
        when(jwtService.extractUsername(jwt)).thenReturn(username);
        when(userService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        when(jwtService.validateToken(jwt, username)).thenReturn(true);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication, "Authentication should not be null");
        assertEquals(mockUserDetails, authentication.getPrincipal(), "Principal should be the mock user");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotSetAuthentication_WhenJwtIsInvalid() throws Exception {
        // Arrange
        String jwt = "invalid-jwt-token";
        String username = "testUser";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwt);  // Add an invalid JWT token
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Ensure the userDetails mock is valid
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn(username);
        when(mockUserDetails.getAuthorities()).thenReturn(List.of());

        // Mock service behavior to return valid userDetails
        when(userService.loadUserByUsername(username)).thenReturn(mockUserDetails);
        when(jwtService.extractUsername(jwt)).thenReturn(username);
        when(jwtService.validateToken(jwt, username)).thenReturn(false);  // Invalid token

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be null");
        verify(filterChain, times(1)).doFilter(request, response);
    }


    @Test
    void doFilterInternal_ShouldNotSetAuthentication_WhenNoAuthorizationHeader() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();  // No Authorization header
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be null");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotSetAuthentication_WhenUsernameIsNull() throws Exception {
        // Arrange
        String jwt = "valid-jwt-token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwt);
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtService.extractUsername(jwt)).thenReturn(null);  // Username is null

        // Act
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be null");
        verify(filterChain, times(1)).doFilter(request, response);
    }
}



