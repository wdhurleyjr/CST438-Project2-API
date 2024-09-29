package com.project2.Project2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    private String bookImageUrl;
    private String amazonUrl;
    private String primaryIsbn;
    private String publisher;
    private int rank;
    private int weeksOnList;

    // Constructor, Getters only

    public Book(String title, String author, String description, String bookImageUrl, String amazonUrl, String primaryIsbn, String publisher, int rank, int weeksOnList) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookImageUrl = bookImageUrl;
        this.amazonUrl = amazonUrl;
        this.primaryIsbn = primaryIsbn;
        this.publisher = publisher;
        this.rank = rank;
        this.weeksOnList = weeksOnList;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public String getAmazonUrl() {
        return amazonUrl;
    }

    public String getPrimaryIsbn() {
        return primaryIsbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getRank() {
        return rank;
    }

    public int getWeeksOnList() {
        return weeksOnList;
    }
}
