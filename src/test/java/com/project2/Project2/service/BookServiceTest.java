package com.project2.Project2.service;

import com.project2.Project2.model.Book;
import com.project2.Project2.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Search Functionality Tests
    @Test
    void searchBooks_ShouldReturnBooks_WhenTitleMatches() {
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "Description 1", "Hardcover Fiction", "https://image1.jpg", "https://amazon1.com");
        when(bookRepository.findByTitleContainingIgnoreCase("The Butcher Game")).thenReturn(Arrays.asList(book1));

        List<Book> results = bookService.searchBooks("The Butcher Game", null, null);

        assertEquals(1, results.size());
        assertEquals("The Butcher Game", results.get(0).getTitle());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("The Butcher Game");
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenAuthorMatches() {
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "Description 1", "Hardcover Fiction", "https://image1.jpg", "https://amazon1.com");
        when(bookRepository.findByAuthorContainingIgnoreCase("Alaina Urquhart")).thenReturn(Arrays.asList(book1));

        List<Book> results = bookService.searchBooks(null, "Alaina Urquhart", null);

        assertEquals(1, results.size());
        assertEquals("Alaina Urquhart", results.get(0).getAuthor());
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Alaina Urquhart");
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenIsbnMatches() {
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "Description 1", "Hardcover Fiction", "https://image1.jpg", "https://amazon1.com");
        when(bookRepository.findByIsbnContainingIgnoreCase("9781638931249")).thenReturn(Arrays.asList(book1));

        List<Book> results = bookService.searchBooks(null, null, "9781638931249");

        assertEquals(1, results.size());
        assertEquals("9781638931249", results.get(0).getIsbn());
        verify(bookRepository, times(1)).findByIsbnContainingIgnoreCase("9781638931249");
    }

    @Test
    void searchBooks_ShouldReturnBooks_WhenTitleAndAuthorMatch() {
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "Description 1", "Hardcover Fiction", "https://image1.jpg", "https://amazon1.com");
        when(bookRepository.findByTitleContainingAndAuthorContainingAllIgnoreCase("The Butcher Game", "Alaina Urquhart"))
                .thenReturn(Arrays.asList(book1));

        List<Book> results = bookService.searchBooks("The Butcher Game", "Alaina Urquhart", null);

        assertEquals(1, results.size());
        assertEquals("The Butcher Game", results.get(0).getTitle());
        assertEquals("Alaina Urquhart", results.get(0).getAuthor());
        verify(bookRepository, times(1)).findByTitleContainingAndAuthorContainingAllIgnoreCase("The Butcher Game", "Alaina Urquhart");
    }

    @Test
    void searchBooks_ShouldReturnEmptyList_WhenNoBooksMatch() {
        when(bookRepository.findByTitleContainingIgnoreCase("Nonexistent Title")).thenReturn(Arrays.asList());

        List<Book> results = bookService.searchBooks("Nonexistent Title", null, null);

        assertTrue(results.isEmpty());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Nonexistent Title");
    }

    @Test
    void searchBooks_ShouldHandleEmptyStrings() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList());

        List<Book> results = bookService.searchBooks("", "", "");

        assertTrue(results.isEmpty());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void searchBooks_ShouldHandleNullParameters() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList());

        List<Book> results = bookService.searchBooks(null, null, null);

        assertTrue(results.isEmpty());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void searchBooks_ShouldHandleCaseInsensitivity() {
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "Description 1", "Hardcover Fiction", "https://image1.jpg", "https://amazon1.com");
        when(bookRepository.findByTitleContainingIgnoreCase("the butcher game")).thenReturn(Arrays.asList(book1));

        List<Book> results = bookService.searchBooks("the butcher game", null, null);

        assertEquals(1, results.size());
        assertEquals("The Butcher Game", results.get(0).getTitle());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("the butcher game");
    }

    // Populate Tests
    @Test
    void populateBooks_ShouldSaveBooksInRepository() {
        bookService.populateBooks();
        verify(bookRepository, times(1)).saveAll(anyList());
    }

    // Amazon URL Generation Tests
    @Test
    void generateAmazonUrlsForAllBooks_ShouldUpdateAmazonUrlsForBooks() {
        Book book1 = new Book("The Butcher Game", "Alaina Urquhart", "9781638931249", "Description 1", "Hardcover Fiction", "https://image1.jpg", "");
        Book book2 = new Book("Educated", "Tara Westover", "9780399590504", "A memoir about growing up in a strict household.", "Biography", "https://image3.jpg", "");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        bookService.generateAmazonUrlsForAllBooks();

        assertEquals("https://www.amazon.com/dp/9781638931249", book1.getAmazonUrl());
        assertEquals("https://www.amazon.com/dp/9780399590504", book2.getAmazonUrl());
        verify(bookRepository, times(1)).findAll();
        verify(bookRepository, times(1)).saveAll(anyList());
    }
}
