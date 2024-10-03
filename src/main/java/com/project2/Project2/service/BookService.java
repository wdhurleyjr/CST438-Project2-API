package com.project2.Project2.service;  // This is the package declaration

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate; // For making API calls

    @Value("${nyt.api.key}")
    private String NYT_API_KEY; // Inject API key from properties

    // Fetch and save books from the NYT API
    public void fetchAndSaveBooks() {
        String apiUrl = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=" + NYT_API_KEY;
        String result = restTemplate.getForObject(apiUrl, String.class);
        
        if (result != null) {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray booksArray = jsonObject.getJSONObject("results").getJSONArray("books");

            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookJson = booksArray.getJSONObject(i);
                Book book = new Book(
                        bookJson.getString("title"),
                        bookJson.getString("author"),
                        bookJson.getString("description"),
                        bookJson.getString("book_image"),
                        bookJson.getString("amazon_product_url"),
                        bookJson.getString("primary_isbn10"),
                        bookJson.getString("primary_isbn13"),
                        bookJson.getString("publisher"),
                        bookJson.getInt("rank"),
                        bookJson.getInt("weeks_on_list")
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
