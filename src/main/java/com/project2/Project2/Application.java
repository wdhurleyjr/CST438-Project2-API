package com.project2.Project2;

import com.project2.Project2.service.BookService;
import com.project2.Project2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		long count = bookRepository.count();
		if (count == 0) {
			System.out.println("No books found in the database. Fetching and saving books...");
			bookService.fetchAndSaveBooks();
			System.out.println("Books fetched and saved.");
		} else {
			System.out.println("Books already exist in the database. No need to fetch.");
		}
	}
}

