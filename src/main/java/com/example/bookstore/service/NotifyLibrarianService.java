package com.example.bookstore.service;

import com.example.bookstore.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyLibrarianService {
    @Autowired
    private EmailService emailService;

    public void notifyLibrarianDelayedReservations(List<Reservation> reservationsDelayed) {
        for (int i = 0; i < reservationsDelayed.size(); ++i) {
            Reservation reservation = reservationsDelayed.get(i);
            String email = reservation.getUnit().getBook().getLibrary().getLibrarian().getEmail();
            String firstName = reservation.getUser().getFirstName();
            String lastName = reservation.getUser().getLastName();
            String phoneNumber = reservation.getUser().getPhoneNumber();
            emailService.sendDelayedReservationMail(email, firstName, lastName, phoneNumber);
        }
    }
}