package com.example.bookstore.dto;

import com.example.bookstore.entities.Status;
import com.example.bookstore.validation.AdvancedValidation;
import com.example.bookstore.validation.BasicValidation;
import com.example.bookstore.validation.ValidDate;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@ValidDate(groups = AdvancedValidation.class)
public class ReservationDTO {
    private Long id;
    @NotNull(groups = BasicValidation.class)
    private LocalDate startDate;
    @NotNull(groups = BasicValidation.class)
    private LocalDate endDate;
    private Status status;
    private UserDTO user;
    private UnitDTO unit;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public UserDTO getUser() { return user; }

    public void setUser(UserDTO user) { this.user = user; }

    public UnitDTO getUnit() { return unit; }

    public void setUnit(UnitDTO unit) { this.unit = unit; }

}

