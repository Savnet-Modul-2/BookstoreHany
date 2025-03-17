package com.example.bookstore.controller;

import com.example.bookstore.dto.LibrarianDTO;
import com.example.bookstore.entities.Librarian;
import com.example.bookstore.entities.User;
import com.example.bookstore.exceptions.AccountNotVerifiedException;
import com.example.bookstore.exceptions.InvalidPasswordException;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.service.LibrarianService;
import com.example.bookstore.validation.BasicValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.bookstore.mapper.LibrarianMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;

    @PostMapping
    public ResponseEntity<?> create(@Validated(BasicValidation.class) @RequestBody LibrarianDTO librarianDTO) {
        Librarian librarianToCreate = LibrarianMapper.librarianDto2Librarian(librarianDTO);
        Librarian createdLibrarian = librarianService.create(librarianToCreate);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(createdLibrarian));
    }

    @GetMapping("/{librarianId}")
    public ResponseEntity<?> getById(@PathVariable(name = "librarianId") Long librarianIdToSearchFor) {
        Librarian foundLibrarian = librarianService.getById(librarianIdToSearchFor);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(foundLibrarian));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Librarian> librarians = librarianService.findAll();
        return ResponseEntity.ok(librarians.stream()
                .map(LibrarianMapper::librarian2LibrarianDto)
                .toList());
    }

    @PutMapping("/{librarianId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "librarianId") Long librarianIdToUpdate, @RequestBody LibrarianDTO librarianBody) {
        Librarian librarianEntity = LibrarianMapper.librarianDto2Librarian(librarianBody);
        Librarian updatedLibrarian = librarianService.updateById(librarianIdToUpdate, librarianEntity);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(updatedLibrarian));
    }

    @DeleteMapping("/{librarianId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "librarianId") Long librarianIdToDelete) {
        librarianService.deleteById(librarianIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("librarianId") Long librarianId, @RequestParam("code") String code) {
        Librarian verifiedLibrarian = librarianService.verifyAccount(librarianId, code);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDto(verifiedLibrarian));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LibrarianDTO librarianDTO) throws InvalidPasswordException, AccountNotVerifiedException {
        Librarian librarianToLogin = LibrarianMapper.librarianDto2Librarian(librarianDTO);
        Librarian loggedLibrarian = librarianService.login(librarianToLogin.getEmail(), librarianToLogin.getPassword());
        return ResponseEntity.ok(loggedLibrarian.getId());
    }
}
