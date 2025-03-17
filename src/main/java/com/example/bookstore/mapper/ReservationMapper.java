package com.example.bookstore.mapper;

import com.example.bookstore.dto.ReservationDTO;
import com.example.bookstore.entities.Reservation;

public class ReservationMapper {
    public static Reservation reservationDTO2Reservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        if (reservationDTO.getUser() != null) {
            reservation.setUser(UserMapper.userDTO2User(reservationDTO.getUser()));
        }
        if (reservationDTO.getUnit() != null) {
            reservation.setUnit(UnitMapper.unitDto2Unit(reservationDTO.getUnit()));
        }
        return reservation;
    }

    public static ReservationDTO reservation2ReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatus(reservation.getStatus());
        if (reservation.getUser() != null) {
            reservationDTO.setUser(UserMapper.user2UserDTO(reservation.getUser()));
        }
        if (reservation.getUnit() != null) {
            reservationDTO.setUnit(UnitMapper.unit2UnitDto(reservation.getUnit()));
        }

        return reservationDTO;
    }
}
