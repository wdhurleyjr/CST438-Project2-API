package com.project2.Project2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RootControllerTest {

    @InjectMocks
    private RootController rootController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void home_ShouldReturnWelcomeMessage() {
        // Arrange
        String expectedMessage = "Welcome to the API! You can access the following resources:\n" +
                "- Users: /api/users\n" +
                "- Books: /api/books\n" +
                "Please refer to the documentation for more details.";

        // Act
        String actualMessage = rootController.home();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }
}

