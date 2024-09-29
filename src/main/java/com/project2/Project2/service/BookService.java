package com.project2.Project2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project2.Project2.repository.BookRepository;
import com.project2.Project2.model.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(String id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            // Update fields
            return bookRepository.save(book);
        });
    }

    public boolean deleteBook(String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

