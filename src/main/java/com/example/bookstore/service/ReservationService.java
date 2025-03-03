package com.example.bookstore.service;

import com.example.bookstore.entities.*;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.ReservationRepository;
import com.example.bookstore.repository.UnitRepository;
import com.example.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public Reservation reserveBook(Long userId, Long bookId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        Unit availableUnit = unitRepository
                .findAvailableUnit(bookId, startDate, endDate)
                .orElseThrow(EntityNotFoundException::new);

        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStatus(Status.PENDING);

        user.addReservation(reservation);
        availableUnit.addReservation(reservation);

        return reservationRepository.save(reservation);
    }
}
