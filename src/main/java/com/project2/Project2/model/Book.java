package com.project2.Project2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private String category;
    private String imageUrl;
    private String amazonUrl;

    // New Fields
    private String publicationDate;
    private double rating;
    private String genre;

    // Default constructor
    public Book() {}

    // Parameterized constructor with all fields
    public Book(String title, String author, String isbn, String description, String category, String imageUrl, String amazonUrl, String publicationDate, double rating, String genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.amazonUrl = amazonUrl;
        this.publicationDate = publicationDate;
        this.rating = rating;
        this.genre = genre;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAmazonUrl() {
        return amazonUrl;
    }

    public void setAmazonUrl(String amazonUrl) {
        this.amazonUrl = amazonUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", amazonUrl='" + amazonUrl + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                '}';
    }
}
