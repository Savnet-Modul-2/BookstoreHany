package com.example.bookstore.controller;

import com.example.bookstore.dto.UnitDTO;
import com.example.bookstore.entities.Unit;
import com.example.bookstore.mapper.UnitMapper;
import com.example.bookstore.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UnitController {
    @Autowired
    private UnitService unitService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UnitDTO unitDto) {
        Unit unitToCreate = UnitMapper.unitDto2Unit(unitDto);
        Unit createdUnit = unitService.create(unitToCreate);
        return ResponseEntity.ok(UnitMapper.unit2UnitDto(createdUnit));
    }
}
