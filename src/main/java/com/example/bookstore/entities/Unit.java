package com.example.bookstore.entities;

import jakarta.persistence.*;

@Entity(name = "unit")
@Table(name = "UNIT", schema = "public")

public class Unit {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "MAXIMUM_BOOKING_DURATION")
    private Integer maximumBookingDuration;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getMaximumBookingDuration() { return maximumBookingDuration; }

    public void setMaximumBookingDuration(Integer maximumBookingDuration) { this.maximumBookingDuration = maximumBookingDuration; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }
}
