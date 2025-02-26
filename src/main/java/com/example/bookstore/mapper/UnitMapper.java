package com.example.bookstore.mapper;

import com.example.bookstore.dto.CreateUnitDTO;
import com.example.bookstore.dto.UnitDTO;
import com.example.bookstore.entities.Unit;

public class UnitMapper {
    public static Unit unitDto2Unit(UnitDTO unitDTO) {
        Unit unit = new Unit();
        unit.setPublisher(unitDTO.getPublisher());
        unit.setMaximumBookingDuration(unitDTO.getMaximumBookingDuration());
        return unit;
    }

    public static UnitDTO unit2UnitDto(Unit unit) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(unit.getId());
        unitDTO.setPublisher(unit.getPublisher());
        unitDTO.setMaximumBookingDuration(unit.getMaximumBookingDuration());
        return unitDTO;
    }

    public static Unit createUnitDtoToUnit(CreateUnitDTO dto) {
        Unit unit = new Unit();
        unit.setPublisher(dto.getPublisher());
        unit.setMaximumBookingDuration(dto.getMaximumBookingDuration());
        return unit;
    }
}
