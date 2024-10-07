package com.project2.Project2.service;

import com.project2.Project2.model.Book;
import com.project2.Project2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void populateBooks() {
        // Implement book population logic (already working)
    }

    // Updated searchBooks Method with Proper Handling
    public List<Book> searchBooks(String title, String author, String isbn) {
        System.out.println("Searching for books with title: " + title + ", author: " + author + ", isbn: " + isbn);

        // Ensure the parameters are not null or empty
        title = (title != null && !title.trim().isEmpty()) ? title : null;
        author = (author != null && !author.trim().isEmpty()) ? author : null;
        isbn = (isbn != null && !isbn.trim().isEmpty()) ? isbn : null;

        if (title != null && author != null) {
            System.out.println("Searching by both title and author...");
            return bookRepository.findByTitleContainingAndAuthorContainingAllIgnoreCase(title, author);
        } else if (title != null) {
            System.out.println("Searching by title only...");
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            System.out.println("Searching by author only...");
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (isbn != null) {
            System.out.println("Searching by ISBN only...");
            return bookRepository.findByIsbnContainingIgnoreCase(isbn);
        } else {
            System.out.println("Returning all books...");
            return bookRepository.findAll();
        }
    }
}
