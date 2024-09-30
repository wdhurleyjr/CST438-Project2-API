package com.project2.Project2.model;

public class NYTBookAPI {
    private String title;
    private String author;
    private String description;
    private String book_image;
    private String amazon_product_url;
    private String primary_isbn10;
    private String primary_isbn13;
    private String publisher;
    private int rank;
    private int weeks_on_list;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookImage() {
        return book_image;
    }

    public void setBookImage(String bookImage) {
        this.book_image = bookImage;
    }

    public String getAmazonProductUrl() {
        return amazon_product_url;
    }

    public void setAmazonProductUrl(String amazonProductUrl) {
        this.amazon_product_url = amazonProductUrl;
    }

    public String getPrimaryIsbn10() {
        return primary_isbn10;
    }

    public void setPrimaryIsbn10(String primaryIsbn10) {
        this.primary_isbn10 = primaryIsbn10;
    }

    public String getPrimaryIsbn13() {
        return primary_isbn13;
    }

    public void setPrimaryIsbn13(String primaryIsbn13) {
        this.primary_isbn13 = primaryIsbn13;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWeeksOnList() {
        return weeks_on_list;
    }

    public void setWeeksOnList(int weeksOnList) {
        this.weeks_on_list = weeksOnList;
    }
}

