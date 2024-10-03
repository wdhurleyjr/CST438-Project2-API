package com.project2.Project2.service;

import com.project2.Project2.model.Book;
import com.project2.Project2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${nyt.api.key}")
    private String NYT_API_KEY;

    public void populateBooks() {
        String apiUrl = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=" + NYT_API_KEY;
        RestTemplate restTemplate = new RestTemplate();

        // Fetch data from the NYT API
        String result = restTemplate.getForObject(apiUrl, String.class);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray booksArray = jsonObject.getJSONObject("results").getJSONArray("books");

        // Loop through the books and save them to MongoDB
        for (int i = 0; i < booksArray.length(); i++) {
            JSONObject bookJson = booksArray.getJSONObject(i);
            Book book = new Book();
            book.setTitle(bookJson.getString("title"));
            book.setAuthor(bookJson.getString("author"));
            book.setIsbn(bookJson.getString("primary_isbn13"));
            book.setDescription(bookJson.getString("description"));
            book.setListName("Hardcover Fiction");

            bookRepository.save(book);
        }
    }
}
