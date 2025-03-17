package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Library;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private BookRepository bookRepository;

    public Library getById(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Library> findAll() {
        return libraryRepository.findAll();
    }

    public Library updateById(Long libraryId, Library libraryBody) {
        Library updatedLibrary = libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);

        updatedLibrary.setName(libraryBody.getName());
        updatedLibrary.setAddress(libraryBody.getAddress());
        updatedLibrary.setPhoneNumber(libraryBody.getPhoneNumber());

        return libraryRepository.save(updatedLibrary);
    }

    public void deleteById(Long libraryId) {
        libraryRepository.deleteById(libraryId);
    }

    public Library addBookToLibrary(Long libraryId, Book book) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);

        library.addBook(book);
        return libraryRepository.save(library);
    }

    public Library addExistingBookToLibrary(Long libraryId, Long bookId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        library.addBook(book);
        return libraryRepository.save(library);
    }

    public void removeBookToLibrary(Long libraryId, Long bookId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(EntityNotFoundException::new);

        Book bookToRemove = library.getBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        library.getBooks().remove(bookToRemove);
        libraryRepository.save(library);
    }
}