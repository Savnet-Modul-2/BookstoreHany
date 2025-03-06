package com.example.bookstore.cronjob;

import com.example.bookstore.entities.Reservation;
import com.example.bookstore.entities.Status;
import com.example.bookstore.repository.ReservationRepository;
import com.example.bookstore.service.NotifyLibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
public class CronJobs {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private NotifyLibrarianService notifyLibrarianService;

    @Scheduled(cron = "*/50 * * * * *") //every 50 seconds
    public void updateReservationStatus() {
        LocalDate now = LocalDate.now();
        List<Reservation> reservationsToBeCanceled = reservationRepository
                .findAllReservationsToBeCanceled(now);
        List<Reservation> reservationsToBeDelayed = reservationRepository
                .findAllReservationsToBeDelayed(now);

        reservationsToBeCanceled.forEach(reservation ->
                reservation.setStatus(Status.CANCELED));
        reservationsToBeDelayed.forEach(reservation ->
                reservation.setStatus(Status.DELAYED));

        reservationRepository.saveAll(reservationsToBeCanceled);
        reservationRepository.saveAll(reservationsToBeDelayed);

        if (!reservationsToBeDelayed.isEmpty()) {
            notifyLibrarianService.notifyLibrarianDelayedReservations(reservationsToBeDelayed);
        }
    }
}
