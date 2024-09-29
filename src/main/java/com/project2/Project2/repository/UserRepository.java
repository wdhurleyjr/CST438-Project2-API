package com.project2.Project2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.project2.Project2.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}

