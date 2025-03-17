package com.example.bookstore.dto;

import com.example.bookstore.validation.ValidDate;
import com.example.bookstore.validation.AdvancedValidation;
import com.example.bookstore.validation.BasicValidation;
import com.example.bookstore.validation.DateNotInThePast;
import com.example.bookstore.validation.ValidDate;
import com.example.bookstore.entities.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@ValidDate(groups = AdvancedValidation.class)
public class ReservationsSearchDTO {
    @NotNull(groups = BasicValidation.class)
    private LocalDate startDate;
    @NotNull(groups = BasicValidation.class)
    private LocalDate endDate;
    private List<Status> reservationStatusList;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Status> getReservationStatusList() {
        return reservationStatusList;
    }

    public void setReservationStatusList(List<Status> reservationStatusList) {
        this.reservationStatusList = reservationStatusList;
    }
}