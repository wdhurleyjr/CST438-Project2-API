package com.project2.Project2.model;

import java.util.List;

public class NYTBookResponse {
    private String status;
    private Results results;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public static class Results {
        private List<NYTBookAPI> books;

        // Getters and Setters
        public List<NYTBookAPI> getBooks() {
            return books;
        }

        public void setBooks(List<NYTBookAPI> books) {
            this.books = books;
        }
    }
}

