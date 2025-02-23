package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.entities.Book;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BookDTO bookDto) {
        Book bookToCreate = BookMapper.bookDto2Book(bookDto);
        Book createdBook = bookService.create(bookToCreate);
        return ResponseEntity.ok(BookMapper.book2BookDto(createdBook));
    }

    @PostMapping("/library/{libraryId}")
    public ResponseEntity<?> create(@PathVariable Long libraryId, @RequestBody BookDTO bookDTO) {
        Book bookToCreate = BookMapper.bookDto2Book(bookDTO);
        Book createdBook = bookService.create(libraryId, bookToCreate);
        return ResponseEntity.ok(BookMapper.book2BookDto(createdBook));
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.findAll(pageable);

        return ResponseEntity.ok(books.map(BookMapper::book2BookDto));
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books.stream().map(BookMapper::book2BookDto).toList());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getById(@PathVariable Long bookId) {
        Book book = bookService.getById(bookId);
        return ResponseEntity.ok(BookMapper.book2BookDto(book));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "bookId") Long bookIdToDelete) {
        bookService.deleteById(bookIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "bookId") Long bookIdToUpdate, @RequestBody BookDTO bookBody) {
        Book bookEntity = BookMapper.bookDto2Book(bookBody);
        Book updatedBook = bookService.updateById(bookIdToUpdate, bookEntity);
        return ResponseEntity.ok(BookMapper.book2BookDto(updatedBook));
    }

    @DeleteMapping("/{bookId}/library")
    public ResponseEntity<?> removeFromLibrary(@PathVariable Long bookId) {
        bookService.removeFromLibrary(bookId);
        return ResponseEntity.noContent().build();
    }
}
