package com.project2.Project2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String home() {
        return "Welcome to the API! You can access the following resources:\n" +
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
                "Please refer to the documentation for more details.";
    }
}

