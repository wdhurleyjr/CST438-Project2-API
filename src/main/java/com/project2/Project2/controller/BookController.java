package com.project2.Project2.controller;

import com.project2.Project2.model.Book;
import com.project2.Project2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/populate")
    public String populateBooks() {
        bookService.populateBooks();
        return "Books data has been populated in MongoDB!";
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "author", required = false) String author,
                                  @RequestParam(value = "isbn", required = false) String isbn) {
        return bookService.searchBooks(title, author, isbn);
    }

    // Generate Amazon URLs Endpoint
    @GetMapping("/generate-amazon-urls")
    public String generateAmazonUrls() {
        bookService.generateAmazonUrlsForAllBooks();
        return "Amazon URLs have been generated for all books!";
    }

    // Update Image URLs Endpoint
    @GetMapping("/update-images")
    public String updateBookImages() {
        bookService.updateBookImages();
        return "Book images have been updated for all books!";
    }
}
