package com.example.bookstore.unitTest.entity;

import com.example.bookstore.entities.Unit;
import com.example.bookstore.entities.Reservation;
import com.example.bookstore.entities.User;
import com.example.bookstore.entities.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ReservationTests {
    private Reservation testReservation;

    @BeforeEach
    public void setUp() {
        testReservation = new Reservation();
    }

    @Test
    public void givenId_GetId_ReturnNotNull() {
        testReservation.setId(1L);

        Assertions.assertThat(testReservation.getId()).isNotNull();
    }

    @Test
    public void givenNothing_GetId_ReturnNull() {
        Assertions.assertThat(testReservation.getId()).isNull();
    }

    @Test
    public void givenStartDate_GetStartDate_ReturnNotNull() {
        testReservation.setStartDate(LocalDate.now());

        Assertions.assertThat(testReservation.getStartDate()).isNotNull();
    }

    @Test
    public void givenNothing_GetStartDate_ReturnNull() {
        Assertions.assertThat(testReservation.getStartDate()).isNull();
    }

    @Test
    public void givenEndDate_GetEndDate_ReturnNotNull() {
        testReservation.setEndDate(LocalDate.now());

        Assertions.assertThat(testReservation.getEndDate()).isNotNull();
    }

    @Test
    public void givenNothing_GetEndDate_ReturnNull() {
        Assertions.assertThat(testReservation.getEndDate()).isNull();
    }

    @Test
    public void givenReservationStatus_GetReservationStatus_ReturnNotNull() {
        testReservation.setStatus(Status.IN_PROGRESS);

        Assertions.assertThat(testReservation.getStatus()).isNotNull();
    }

    @Test
    public void givenNothing_GetReservationStatus_ReturnNull() {
        Assertions.assertThat(testReservation.getStatus()).isNull();
    }

//    @Test
//    public void givenUser_GetReservedUser_ReturnNotNull() {
//        testReservation.setReservedUser(new User());
//
//        Assertions.assertThat(testReservation.getReservedUser()).isNotNull();
//    }
//
//    @Test
//    public void givenNothing_GetReservedUser_ReturnNull() {
//        Assertions.assertThat(testReservation.getReservedUser()).isNull();
//    }

//    @Test
//    public void givenBookUnit_GetReservedUnit_ReturnNotNull() {
//        testReservation.setReservedUnit(new Unit());
//
//        Assertions.assertThat(testReservation.getUnit()).isNotNull();
//    }
//
//    @Test
//    public void givenNothing_GetReservedUnit_ReturnNull() {
//        Assertions.assertThat(testReservation.getReservedUnit()).isNull();
//    }
}
