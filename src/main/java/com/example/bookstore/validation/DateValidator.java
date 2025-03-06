package com.example.bookstore.validation;

import com.example.bookstore.dto.ReservationDTO;
import com.example.bookstore.entities.Reservation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, ReservationDTO> {
    @Override
    public void initialize(ValidDate validDate) {
    }

    @Override
    public boolean isValid(ReservationDTO reservationDTO, ConstraintValidatorContext context) {
        return !reservationDTO.getStartDate().isAfter(reservationDTO.getEndDate());
    }
}