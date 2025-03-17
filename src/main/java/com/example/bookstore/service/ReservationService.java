package com.example.bookstore.service;

import com.example.bookstore.dto.ReservationsSearchDTO;
import com.example.bookstore.entities.*;
import com.example.bookstore.exceptions.MissingArgumentException;
import com.example.bookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
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
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private EmailService emailService;

    public Reservation reserveBook(Long userId, Long bookId, Reservation reservation) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        Unit availableUnit = unitRepository
                .findAvailableUnit(bookId, reservation.getStartDate(), reservation.getEndDate())
                .orElseThrow(EntityNotFoundException::new);

        if (availableUnit.getMaximumBookingDuration() < Duration.between(reservation.getStartDate().atStartOfDay(), reservation.getStartDate().atStartOfDay()).toDays()) {
            throw new UnsupportedOperationException("Reservation too long, must be maximum " + availableUnit.getMaximumBookingDuration() + " days");
        }

        Reservation newReservation = new Reservation();
        newReservation.setStartDate(reservation.getStartDate());
        newReservation.setEndDate(reservation.getEndDate());
        newReservation.setStatus(Status.PENDING);

        user.addReservation(newReservation);
        availableUnit.addReservation(newReservation);

        return reservationRepository.save(newReservation);
    }

    public Reservation getById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservationStatus(Long librarianId, Long reservationId, Reservation reservation) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(EntityNotFoundException::new);

        Reservation newReservation = reservationRepository.findById(reservationId)
                .orElseThrow(EntityNotFoundException::new);

        if (!Objects.equals(librarian.getLibrary().getId(), newReservation.getUnit().getBook().getLibrary().getId())) {
            throw new IllegalArgumentException("Librarian does not have access to the library!");
        }

        if (!newReservation.getStatus().isNextStatePossible(reservation.getStatus())) {
            throw new EntityNotFoundException("Cannot update status of reservation");
        }

        newReservation.setStatus(reservation.getStatus());
        return reservationRepository.save(newReservation);
    }

    public List<Reservation> updateExpiredPendingReservations() {
        List<Reservation> reservations = reservationRepository.findByReservationStatusAndStartDateBefore(Status.PENDING, LocalDate.now());
        reservations.forEach(reservation -> reservation.setStatus(Status.CANCELED));
        return reservationRepository.saveAll(reservations);
    }

    public List<Reservation> updateExpiredInProgressReservations() {
        List<Reservation> reservations = reservationRepository.findByReservationStatusAndEndDateBefore(Status.IN_PROGRESS, LocalDate.now());
        reservations.forEach(reservation -> reservation.setStatus(Status.DELAYED));
        reservationRepository.saveAll(reservations);

        reservations.forEach(reservation -> {
            String userEmail = reservation.getUser().getEmail();
            String userPhone = reservation.getUser().getPhoneNumber();
            String bookTitle = reservation.getUnit().getBook().getTitle();
            String librarianEmail = reservation.getUnit().getBook().getLibrary().getLibrarian().getEmail();

            String userSubject = "Book to return";
            String userText = " You need to return the book '" + bookTitle + "'.";
            emailService.sendEmail(userEmail, userSubject, userText);

            String librarianSubject = "Unreturned Book";
            String librarianText = "The user " + reservation.getUser().getFirstName() + " "
                    + reservation.getUser().getLastName() +
                    " with phone number " + userPhone + " has not returned the book '" + bookTitle +
                    "'. Please contact them.";
            emailService.sendEmail(librarianEmail, librarianSubject, librarianText);
        });

        return reservations;
    }

    public Page<Reservation> getLibraryReservationsByStartDateAndEndDate(Long libraryId, Integer pageNumber, Integer pageSize, ReservationsSearchDTO reservationsSearchDTO) throws MissingArgumentException {
        if (reservationsSearchDTO.getStartDate() == null || reservationsSearchDTO.getEndDate() == null) {
            throw new MissingArgumentException("Start date or end date is missing");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.findReservationsByStartDateAndEndDate(libraryId, reservationsSearchDTO.getStartDate(), reservationsSearchDTO.getEndDate(), reservationsSearchDTO.getReservationStatusList(), pageable);
    }

    public Page<Reservation> getUserReservationsByStatus(Long userId, Integer pageNumber, Integer pageSize, ReservationsSearchDTO reservationsSearchDTO) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.findReservationsByUserAndReservationStatus(userId, reservationsSearchDTO.getStartDate(), reservationsSearchDTO.getEndDate(), reservationsSearchDTO.getReservationStatusList(), pageable);
    }
}