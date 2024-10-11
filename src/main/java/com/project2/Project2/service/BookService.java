package com.project2.Project2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project2.Project2.model.Book;
import com.project2.Project2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieve all books from the MongoDB collection.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Populate MongoDB collection with dummy book data.
     */
    public void populateBooks() {
        // Creating dummy books for population
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "A thrilling novel of crime and suspense.", "Hardcover Fiction", "https://image1.jpg", "");
        Book book2 = new Book("The Science of Interstellar", "Kip Thorne", "9780393351378", "Exploring the science behind the movie Interstellar.", "Science", "https://image2.jpg", "");
        Book book3 = new Book("Educated", "Tara Westover", "9780399590504", "A memoir by Tara Westover about growing up in a strict and abusive household.", "Biography", "https://image3.jpg", "");

        // Saving the books into the MongoDB collection
        bookRepository.saveAll(Arrays.asList(book1, book2, book3));
    }

    /**
     * Search for books based on title, author, or ISBN.
     */
    public List<Book> searchBooks(String title, String author, String isbn) {
        // Treat empty strings as null to avoid unintended query behavior
        if (title != null && title.trim().isEmpty()) {
            title = null;
        }
        if (author != null && author.trim().isEmpty()) {
            author = null;
        }
        if (isbn != null && isbn.trim().isEmpty()) {
            isbn = null;
        }

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

    /**
     * Generate Amazon URLs for all books in the database based on their ISBNs.
     */
    public void generateAmazonUrlsForAllBooks() {
        // Fetch all books from the repository
        List<Book> books = bookRepository.findAll();

        // Iterate through each book and update its Amazon URL if ISBN is present
        for (Book book : books) {
            if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                // Generate Amazon URL using the ISBN
                String amazonUrl = "https://www.amazon.com/dp/" + book.getIsbn();
                book.setAmazonUrl(amazonUrl);
            }
        }
        // Save the updated books back to the repository
        bookRepository.saveAll(books);
    }

    /**
     * Update the image URLs for all books in the database based on their ISBNs.
     * This method uses a Google Books API to retrieve image URLs.
     */
    public void updateBookImages() {
        // Fetch all books from the repository
        List<Book> books = bookRepository.findAll();

        // Create a RestTemplate for making HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // Iterate through each book and update its image URL based on ISBN
        for (Book book : books) {
            if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                try {
                    // Build the Google Books API URL using the ISBN
                    String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + book.getIsbn();

                    // Make a request to the Google Books API
                    String response = restTemplate.getForObject(apiUrl, String.class);

                    // Parse the response to extract the image URL
                    String imageUrl = parseImageUrlFromResponse(response);
                    if (imageUrl != null) {
                        book.setImageUrl(imageUrl);
                    }

                } catch (Exception e) {
                    System.out.println("Failed to retrieve image for ISBN: " + book.getIsbn());
                }
            }
        }

        // Save the updated books back to the repository
        bookRepository.saveAll(books);
    }

    /**
     * Parses the image URL from the Google Books API response.
     * Extracts the "thumbnail" URL from the first item in the response.
     *
     * @param response The JSON response string from the Google Books API.
     * @return The extracted image URL, or null if not found.
     */
    private String parseImageUrlFromResponse(String response) {
        try {
            // Parse the JSON response using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // Check if items are present in the response
            if (rootNode.has("items") && rootNode.get("items").isArray()) {
                JsonNode firstItem = rootNode.get("items").get(0);
                JsonNode volumeInfo = firstItem.get("volumeInfo");

                // Check if volumeInfo contains imageLinks
                if (volumeInfo != null && volumeInfo.has("imageLinks")) {
                    JsonNode imageLinks = volumeInfo.get("imageLinks");

                    // Extract the "thumbnail" field from imageLinks
                    if (imageLinks.has("thumbnail")) {
                        return imageLinks.get("thumbnail").asText();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to parse image URL from response: " + e.getMessage());
        }
        return null; // Return null if no image URL is found
    }
}
