package com.project2.Project2.scheduler;

import com.project2.Project2.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NYTBookScheduler {

    private final BookService bookService;

    public NYTBookScheduler(BookService bookService) {
        this.bookService = bookService;
    }

    // Schedule to run every week on Sunday at midnight
    @Scheduled(cron = "0 0 0 * * SUN") // Cron expression for weekly
    public void fetchAndSaveBooks() {
        bookService.fetchAndSaveBooks();
    }
}
