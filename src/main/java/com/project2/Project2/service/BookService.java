package com.project2.Project2.service;

import com.project2.Project2.model.Book;
import com.project2.Project2.repository.BookRepository;
import com.project2.Project2.model.NYTBookResponse;
import com.project2.Project2.model.NYTBookAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate; // For making API calls

    // Fetch and save books from the NYT API
    public void fetchAndSaveBooks() {
        String apiUrl = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=VuEOhETDThDTa2VB1MvEsLgwTmNMJkeh";
        NYTBookResponse response = restTemplate.getForObject(apiUrl, NYTBookResponse.class);

        if (response != null && response.getResults() != null && response.getResults().getBooks() != null) {
            for (NYTBookAPI bookAPI : response.getResults().getBooks()) {
                Book book = new Book(
                        bookAPI.getTitle(),
                        bookAPI.getAuthor(),
                        bookAPI.getDescription(),
                        bookAPI.getBookImage(),
                        bookAPI.getAmazonProductUrl(),
                        bookAPI.getPrimaryIsbn10(),
                        bookAPI.getPrimaryIsbn13(),
                        bookAPI.getPublisher(),
                        bookAPI.getRank(),
                        bookAPI.getWeeksOnList()
                );
                bookRepository.save(book);
            }
        }
    }

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
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setDescription(updatedBook.getDescription());
            book.setBookImageUrl(updatedBook.getBookImageUrl());
            book.setAmazonUrl(updatedBook.getAmazonUrl());
            book.setPrimaryIsbn10(updatedBook.getPrimaryIsbn10());
            book.setPrimaryIsbn13(updatedBook.getPrimaryIsbn13());
            book.setPublisher(updatedBook.getPublisher());
            book.setRank(updatedBook.getRank());
            book.setWeeksOnList(updatedBook.getWeeksOnList());
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


