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
                "- Books: /api/books\n" +
                "Please refer to the documentation for more details.";
    }
}
