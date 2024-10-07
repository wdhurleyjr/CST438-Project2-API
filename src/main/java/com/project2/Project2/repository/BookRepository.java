package com.project2.Project2.repository;

import com.project2.Project2.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByIsbnContainingIgnoreCase(String isbn);
    List<Book> findByTitleContainingAndAuthorContainingAllIgnoreCase(String title, String author);
}
