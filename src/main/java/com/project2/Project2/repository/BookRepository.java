package com.project2.Project2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.project2.Project2.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
    // Additional query methods (if needed) can be defined here
}

