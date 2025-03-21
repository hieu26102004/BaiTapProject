package com.example.baitapproject.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {

    @SerializedName("book_id")
    private int book_id;
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("genre")
    private String genre;
    @SerializedName("publisher")
    private String publisher;

    @SerializedName("publication_date")
    private LocalDate publication_date;

    @SerializedName("coverUrl")
    private String coverUrl;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
