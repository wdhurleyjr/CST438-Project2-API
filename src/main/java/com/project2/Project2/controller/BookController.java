package com.project2.Project2.controller;

import com.project2.Project2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books") // This maps to "/api/books"
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/populate") // This maps to "/api/books/populate"
    public String populateBooks() {
        bookService.populateBooks();
        return "Books data has been populated in MongoDB!";
    }
}
