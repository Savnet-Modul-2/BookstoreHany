package com.example.bookstore.service;

import com.example.bookstore.entities.*;
import com.example.bookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

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
    @Autowired
    private LibrarianRepository librarianRepository;

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

    public Reservation changeStatus(Long librarianId, Long reservationId, Reservation reservationToUpdate) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new EntityNotFoundException("Librarian not found"));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if (!Objects.equals(librarian.getLibrary().getId(),
                reservation.getUnit().getBook().getLibrary().getId())) {
            throw new IllegalStateException("No access for librarian to the reservation");
        }

        Status currentStatus = reservation.getStatus();
        if (!currentStatus.isNextStatePossible(reservationToUpdate.getStatus())) {
            throw new IllegalStateException("Not possible");
        }
        reservation.setStatus(reservationToUpdate.getStatus());

        return reservationRepository.save(reservation);
    }
}
