package com.example.bookstore.controller;

import com.example.bookstore.dto.ReservationDTO;
import com.example.bookstore.entities.Reservation;
import com.example.bookstore.mapper.ReservationMapper;
import com.example.bookstore.service.ReservationService;
import com.example.bookstore.validation.ValidationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<?> reserveBook(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "bookId") Long bookId,
                                         @Validated(ValidationOrder.class) @RequestBody ReservationDTO updatedReservationDTO) {
        Reservation createdReservation = reservationService.reserveBook(userId, bookId, updatedReservationDTO.getStartDate(), updatedReservationDTO.getEndDate());
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(createdReservation));
    }

    @PutMapping("/{librarianId}/{reservationId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long librarianId,
                                          @PathVariable Long reservationId,
                                          @Validated(ValidationOrder.class) @RequestBody ReservationDTO updatedReservationDTO) {
        Reservation reservationToUpdate = ReservationMapper.reservationDTO2Reservation(updatedReservationDTO);
        Reservation updatedReservation = reservationService.changeStatus(librarianId,
                reservationId, reservationToUpdate);
        return ResponseEntity.ok(ReservationMapper.reservation2ReservationDTO(updatedReservation));
    }
}
