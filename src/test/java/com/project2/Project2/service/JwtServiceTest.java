package com.project2.Project2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String testUsername = "testuser";
    private Collection<GrantedAuthority> authorities;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(testUsername, authorities);
        assertNotNull(token, "Token should not be null");

        // Verify that the token contains the correct username
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(testUsername, extractedUsername, "Extracted username should match the test username");

        // Verify that the token contains roles
        List<GrantedAuthority> extractedRoles = jwtService.extractRoles(token);
        assertEquals(1, extractedRoles.size(), "There should be exactly one role in the token");
        assertEquals("ROLE_USER", extractedRoles.get(0).getAuthority(), "The role should be ROLE_USER");
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(testUsername, authorities);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(testUsername, extractedUsername, "The extracted username should match the provided username");
    }

    @Test
    public void testExtractRoles() {
        String token = jwtService.generateToken(testUsername, authorities);
        List<GrantedAuthority> extractedRoles = jwtService.extractRoles(token);
        assertEquals(1, extractedRoles.size(), "The token should contain one role");
        assertEquals("ROLE_USER", extractedRoles.get(0).getAuthority(), "The role should be ROLE_USER");
    }

    @Test
    public void testValidateToken() {
        String token = jwtService.generateToken(testUsername, authorities);
        boolean isValid = jwtService.validateToken(token, testUsername);
        assertTrue(isValid, "Token should be valid for the correct username");

        boolean isInvalid = jwtService.validateToken(token, "wronguser");
        assertFalse(isInvalid, "Token should not be valid for a wrong username");
    }

    @Test
    public void testIsTokenExpiredWithReflection() throws Exception {
        // Use reflection to access the private isTokenExpired method
        Method isTokenExpiredMethod = JwtService.class.getDeclaredMethod("isTokenExpired", String.class);
        isTokenExpiredMethod.setAccessible(true);  // Make private method accessible

        // Access the SECRET_KEY from the JwtService instance using reflection
        Field secretKeyField = JwtService.class.getDeclaredField("SECRET_KEY");
        secretKeyField.setAccessible(true);
        SecretKey secretKey = (SecretKey) secretKeyField.get(jwtService);

        // Record the current time
        long now = System.currentTimeMillis();

        // Create a token with a 1-second expiration time using the same SECRET_KEY
        String shortLivedToken = Jwts.builder()
                .setSubject(testUsername)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000))  // 1-second expiration
                .signWith(secretKey)
                .compact();

        // Immediately check that the token is not expired (should still be valid)
        boolean isExpired = (boolean) isTokenExpiredMethod.invoke(jwtService, shortLivedToken);
        assertFalse(isExpired, "Token should not be expired immediately after creation");

        // Wait for at least 1 second to ensure expiration (no exact sleep)
        while (System.currentTimeMillis() < now + 1000) {
            // Busy-wait until the token should be expired
        }

        // Validate again after expiration time has passed
        isExpired = (boolean) isTokenExpiredMethod.invoke(jwtService, shortLivedToken);
        assertTrue(isExpired, "Token should be expired after the expiration time");
    }

    @Test
    public void testExtractAllClaimsWithReflection() throws Exception {
        // Use reflection to access the private extractAllClaims method
        Method extractAllClaimsMethod = JwtService.class.getDeclaredMethod("extractAllClaims", String.class);
        extractAllClaimsMethod.setAccessible(true);  // Make private method accessible

        // Generate token
        String token = jwtService.generateToken(testUsername, authorities);

        // Invoke the private method using reflection
        Claims claims = (Claims) extractAllClaimsMethod.invoke(jwtService, token);

        assertNotNull(claims, "Claims should not be null");
        assertEquals(testUsername, claims.getSubject(), "Username should match the token subject");

        List<String> roles = claims.get("roles", List.class);
        assertEquals(1, roles.size(), "There should be one role");
        assertEquals("ROLE_USER", roles.get(0), "The role should be ROLE_USER");
    }
}
