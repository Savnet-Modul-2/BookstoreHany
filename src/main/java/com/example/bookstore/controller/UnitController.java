package com.example.bookstore.controller;

import com.example.bookstore.dto.CreateUnitDTO;
import com.example.bookstore.dto.UnitDTO;
import com.example.bookstore.entities.Unit;
import com.example.bookstore.mapper.UnitMapper;
import com.example.bookstore.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateUnitDTO unitDto) {
        Unit unitToCreate = UnitMapper.createUnitDtoToUnit(unitDto);
        List<Unit> createdUnits = unitService.create(unitDto.getBookId(), unitDto.getNumberOfUnits(), unitToCreate);
        return ResponseEntity.ok(createdUnits.stream()
                .map(UnitMapper::unit2UnitDto)
                .toList());
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<?> getById(@PathVariable(name = "unitId") Long unitId) {
        Unit foundUnit = unitService.getById(unitId);
        return ResponseEntity.ok(UnitMapper.unit2UnitDto(foundUnit));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "pageNumber", required = false) Integer pageNumber, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (pageSize != null && pageNumber != null) {
            Page<Unit> unitPage = unitService.findAll(PageRequest.of(pageNumber, pageSize));
            return ResponseEntity.ok(unitPage.map(UnitMapper::unit2UnitDto));
        }
        List<Unit> units = unitService.findAll();
        return ResponseEntity.ok(units.stream()
                .map(UnitMapper::unit2UnitDto)
                .toList());
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "unitId") Long unitId, @RequestBody UnitDTO unitBody) {
        Unit unitEntity = UnitMapper.unitDto2Unit(unitBody);
        Unit updatedUnit = unitService.updateById(unitId, unitEntity);
        return ResponseEntity.ok(UnitMapper.unit2UnitDto(updatedUnit));
    }

    @DeleteMapping("/{unitId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "unitId") Long unitId) {
        unitService.deleteById(unitId);
        return ResponseEntity.noContent().build();
    }

}
