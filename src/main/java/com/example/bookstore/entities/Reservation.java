package com.example.bookstore.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "reservation")
@Table(name = "RESERVATION", schema = "public")
public class Reservation {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Unit getUnit() { return unit; }

    public void setUnit(Unit unit) { this.unit = unit; }
}