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
    private String primaryIsbn10;
    private String primaryIsbn13;
    private String publisher;
    private int rank;
    private int weeksOnList;

    // Constructor
    public Book(String title, String author, String description, String bookImageUrl, String amazonUrl, String primaryIsbn10, String primaryIsbn13, String publisher, int rank, int weeksOnList) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookImageUrl = bookImageUrl;
        this.amazonUrl = amazonUrl;
        this.primaryIsbn10 = primaryIsbn10;
        this.primaryIsbn13 = primaryIsbn13;
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

    public String getPrimaryIsbn10() {
        return primaryIsbn10;
    }

    public String getPrimaryIsbn13() {
        return primaryIsbn13;
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

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public void setAmazonUrl(String amazonUrl) {
        this.amazonUrl = amazonUrl;
    }

    public void setPrimaryIsbn10(String primaryIsbn10) {
        this.primaryIsbn10 = primaryIsbn10;
    }

    public void setPrimaryIsbn13(String primaryIsbn13) {
        this.primaryIsbn13 = primaryIsbn13;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setWeeksOnList(int weeksOnList) {
        this.weeksOnList = weeksOnList;
    }
}
