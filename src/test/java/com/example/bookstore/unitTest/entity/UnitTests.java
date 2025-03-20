package com.example.bookstore.unitTest.entity;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Unit;
import com.example.bookstore.entities.Reservation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UnitTests {
    private Unit testUnit;

    @BeforeEach
    public void setUp() {
        testUnit = new Unit();
    }

    @Test
    public void givenId_GetId_ReturnNotNull() {
        testUnit.setId(1L);

        Assertions.assertThat(testUnit.getId()).isNotNull();
    }

    @Test
    public void givenNothing_GetId_ReturnNull() {
        Assertions.assertThat(testUnit.getId()).isNull();
    }

    @Test
    public void givenPublisher_GetPublisher_ReturnNotEmpty() {
        testUnit.setPublisher("testPublisher");

        Assertions.assertThat(testUnit.getPublisher()).isNotEmpty();
    }

    @Test
    public void givenNothing_GetPublisher_ReturnNull() {
        Assertions.assertThat(testUnit.getPublisher()).isNull();
    }

    @Test
    public void givenMaximumReservationDuration_GetMaximumBookingDuration_ReturnGreaterThan() {
        testUnit.setMaximumBookingDuration(3);

        Assertions.assertThat(testUnit.getMaximumBookingDuration()).isGreaterThan(0);
    }

    @Test
    public void givenNothing_GetMaximumBookingDuration_ReturnIsZero() {
        Assertions.assertThat(testUnit.getMaximumBookingDuration()).isZero();
    }

    @Test
    public void givenBook_GetBook_ReturnNotNull() {
        testUnit.setBook(new Book());

        Assertions.assertThat(testUnit.getBook()).isNotNull();
    }

    @Test
    public void givenNothing_GetBook_ReturnNull() {
        Assertions.assertThat(testUnit.getBook()).isNull();
    }

    @Test
    public void givenReservationList_GetReservations_ReturnNotEmpty() {
        testUnit.setReservations(List.of(new Reservation()));

        Assertions.assertThat(testUnit.getReservations()).isNotEmpty();
    }

    @Test
    public void givenNothing_GetReservations_ReturnIsEmpty() {
        Assertions.assertThat(testUnit.getReservations()).isEmpty();
    }
}
