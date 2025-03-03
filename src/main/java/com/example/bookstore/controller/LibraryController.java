package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.dto.LibraryDTO;
import com.example.bookstore.entities.Library;
import com.example.bookstore.mapper.BookMapper;
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

    @GetMapping("/{libraryId}")
    public ResponseEntity<?> getById(@PathVariable(name = "libraryId") Long libraryIdToSearchFor) {
        Library foundLibrary = libraryService.getById(libraryIdToSearchFor);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(foundLibrary));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Library> library = libraryService.findAll();
        return ResponseEntity.ok(library.stream()
                .map(LibraryMapper::library2LibraryDto)
                .toList());
    }

    @PutMapping("/{libraryId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "libraryId") Long libraryIdToUpdate, @RequestBody LibraryDTO libraryBody) {
        Library libraryEntity = LibraryMapper.libraryDto2Library(libraryBody);
        Library updatedLibrary = libraryService.updateById(libraryIdToUpdate, libraryEntity);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(updatedLibrary));
    }

    @DeleteMapping("/{libraryId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "libraryId") Long libraryIdToDelete) {
        libraryService.deleteById(libraryIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-book/{libraryId}")
    public ResponseEntity<?> addBookToLibrary(@PathVariable(name = "libraryId") Long libraryID, @RequestBody BookDTO bookDTO) {
        Library library = libraryService.addBookToLibrary(libraryID, BookMapper.bookDto2Book(bookDTO));
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(library));
    }

    @PutMapping("/{libraryId}/add-book/{bookId}")
    public ResponseEntity<?> addExistingBookToLibrary(@PathVariable Long libraryId, @PathVariable Long bookId) {
        Library updatedLibrary = libraryService.addExistingBookToLibrary(libraryId, bookId);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(updatedLibrary));
    }

    @DeleteMapping("/{libraryId}/remove-book/{bookId}")
    public ResponseEntity<?> removeBookFromLibrary(@PathVariable(name = "libraryId") Long libraryID, @PathVariable(name = "bookId") Long bookID) {
        libraryService.removeBookToLibrary(libraryID, bookID);
        return ResponseEntity.noContent().build();
    }
}
