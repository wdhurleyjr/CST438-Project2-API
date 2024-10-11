package com.project2.Project2.repository;

import com.project2.Project2.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    // Query methods for basic search
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByIsbnContainingIgnoreCase(String isbn);
    List<Book> findByTitleContainingAndAuthorContainingAllIgnoreCase(String title, String author);

    // Additional query methods can be added here for new fields if required
    // Example:
    // List<Book> findByGenreContainingIgnoreCase(String genre);
}
