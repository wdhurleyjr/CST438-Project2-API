package com.project2.Project2.service;

import com.project2.Project2.model.Book;
import com.project2.Project2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void populateBooks() {
        // Creating dummy books for population
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "A thrilling novel of crime and suspense.", "Hardcover Fiction", "https://image1.jpg", "https://amazon1.com");
        Book book2 = new Book("The Science of Interstellar", "Kip Thorne", "9780393351378", "Exploring the science behind the movie Interstellar.", "Science", "https://image2.jpg", "https://amazon2.com");
        Book book3 = new Book("Educated", "Tara Westover", "9780399590504", "A memoir by Tara Westover about growing up in a strict and abusive household.", "Biography", "https://image3.jpg", "https://amazon3.com");

        // Saving the books into the MongoDB collection
        bookRepository.saveAll(Arrays.asList(book1, book2, book3));
    }

    public List<Book> searchBooks(String title, String author, String isbn) {
        // Treat empty strings as null
        title = (title == null || title.trim().isEmpty()) ? null : title;
        author = (author == null || author.trim().isEmpty()) ? null : author;
        isbn = (isbn == null || isbn.trim().isEmpty()) ? null : isbn;
    
        if (title != null && author != null) {
            return bookRepository.findByTitleContainingAndAuthorContainingAllIgnoreCase(title, author);
        } else if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else if (isbn != null) {
            return bookRepository.findByIsbnContainingIgnoreCase(isbn);
        } else {
            return bookRepository.findAll();
        }
    }
}    
