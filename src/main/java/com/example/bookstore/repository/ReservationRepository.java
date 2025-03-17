package com.example.bookstore.repository;

import com.example.bookstore.entities.Reservation;
import com.example.bookstore.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservationStatusAndStartDateBefore(Status reservationStatus, LocalDate date);

    List<Reservation> findByReservationStatusAndEndDateBefore(Status reservationStatus, LocalDate date);

    @Query(value = """
         SELECT reservation FROM reservation reservation
         WHERE (reservation.startDate >= :startDate AND reservation.endDate <= :endDate)
            AND (:reservationStatusList IS NULL OR reservation.reservationStatus IN :reservationStatusList)
            AND reservation.unit.book.library.id = :libraryId
            """)
    Page<Reservation> findReservationsByStartDateAndEndDate(Long libraryId, LocalDate startDate, LocalDate endDate, List<Status> statusList, Pageable pageable);

    @Query(value = """
         SELECT reservation FROM reservation reservation
         WHERE reservation.user.id = :userId
            AND (cast(:startDate as date) IS NULL OR reservation.startDate >= :startDate) 
            AND (cast(:endDate as date) IS NULL OR reservation.endDate <= :endDate)
            AND (:reservationStatusList IS NULL OR reservation.reservationStatus IN :reservationStatusList)
            """)
    Page<Reservation> findReservationsByUserAndReservationStatus(Long userId, LocalDate startDate, LocalDate endDate, List<Status> statusList, Pageable pageable);
}
