package com.example.bookstore.repository;

import com.example.bookstore.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = """
         SELECT * FROM reservation reservation
         WHERE reservation.start_date < :dateNow
         AND reservation.reservation_status = 'PENDING'
     """, nativeQuery = true)
    List<Reservation> findAllReservationsToBeCanceled(LocalDate dateNow);

    @Query(value = """
         SELECT * FROM reservation reservation
         WHERE reservation.end_date < :dateNow
         AND reservation.reservation_status = 'IN_PROGRESS'
     """, nativeQuery = true)
    List<Reservation> findAllReservationsToBeDelayed(LocalDate dateNow);
}
