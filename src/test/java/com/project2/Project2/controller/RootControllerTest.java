package com.project2.Project2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


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
        String expectedMessage = "Welcome to the API! You can access the following resources:\n" +
                "- Users: /api/users\n" +
                "   - GET: /api/users (Retrieve all users)\n" +
                "   - GET: /api/users/{id} (Retrieve user by ID)\n" +
                "   - POST: /api/users (Create a new user)\n" +
                "   - PUT: /api/users/{id} (Update user by ID)\n" +
                "   - DELETE: /api/users/{id} (Delete user by ID)\n" +
                "   - POST: /api/users/{id}/wishlist/{bookId} (Add book to user's wishlist)\n" +
                "   - DELETE: /api/users/{id}/wishlist/{bookId} (Remove book from user's wishlist)\n" +
                "   - GET: /api/users/{id}/wishlist (Get user's wishlist)\n" +
                "- Books: /api/books\n" +
                "   - GET: /api/books (Retrieve all books)\n" +
                "   - GET: /api/books/populate (Populate books in the database)\n" +
                "   - GET: /api/books/search?title=&author=&isbn= (Search for books by title, author, or ISBN)\n" +
                "- Admin: /api/admin\n" +
                "   - PUT: /api/admin/{id}/{role} (Assign role to user)\n" +
                "   - DELETE: /api/admin/{id}/{role} (Remove role from user)\n" +
                "   - GET: /api/admin/users (Retrieve all users - Admin view)\n" +
                "   - GET: /api/admin/{id}/roles (Get user roles)\n" +
                "Please refer to the documentation for more details.";
        
        String actualMessage = rootController.home();
        
        assertEquals(expectedMessage, actualMessage);
    }
    
}

