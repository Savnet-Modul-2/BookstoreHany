package com.example.bookstore.controller;

import com.example.bookstore.dto.LibraryDTO;
import com.example.bookstore.entities.Library;
import com.example.bookstore.mapper.LibraryMapper;
import com.example.bookstore.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody LibraryDTO libraryDTO) {
        Library libraryToCreate = LibraryMapper.libraryDto2Library(libraryDTO);
        Library createdLibrary = libraryService.create(libraryToCreate);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(createdLibrary));
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Library> libraryList = libraryService.findAll();
        return ResponseEntity.ok(libraryList.stream().map(LibraryMapper::library2LibraryDto).toList());
    }
}
