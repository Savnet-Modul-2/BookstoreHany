package com.example.bookstore.dto;

import com.example.bookstore.entities.Genre;
import com.example.bookstore.entities.Library;

import java.time.LocalDateTime;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime appearanceDate;
    private Integer nrOfPages;
    private Genre genre;
    private String language;
    private LibraryDTO library;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getAppearanceDate() {
        return appearanceDate;
    }

    public void setAppearanceDate(LocalDateTime appearanceDate) {
        this.appearanceDate = appearanceDate;
    }

    public Integer getNrOfPages() {
        return nrOfPages;
    }

    public void setNrOfPages(Integer nrOfPages) {
        this.nrOfPages = nrOfPages;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LibraryDTO getLibrary() { return library; }

    public void setLibrary(LibraryDTO library) { this.library = library; }
}
