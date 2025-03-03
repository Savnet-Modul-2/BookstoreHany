package com.example.bookstore.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "exemplary")
    private List<Reservation> reservations = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getMaximumBookingDuration() { return maximumBookingDuration; }

    public void setMaximumBookingDuration(Integer maximumBookingDuration) { this.maximumBookingDuration = maximumBookingDuration; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setUnit(this);
    }
}
