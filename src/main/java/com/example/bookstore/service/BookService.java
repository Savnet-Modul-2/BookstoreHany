package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Library;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book create(Book book) {
        if (book.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new book that you want to create");
        }
        return bookRepository.save(book);
    }

    public Book getById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> findAll(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public Page<Book> search(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public List<Book> search(String bookAuthor, String bookTitle) {
        return bookRepository.findBooks(bookAuthor, bookTitle);
    }

    public Book updateById(Long bookId, Book bookEntity) {
        Book updatedBook = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        updatedBook.setTitle(bookEntity.getTitle());
        updatedBook.setAuthor(bookEntity.getAuthor());
        updatedBook.setAppearanceDate(bookEntity.getAppearanceDate());
        updatedBook.setNrOfPages(bookEntity.getNrOfPages());
        updatedBook.setGenre(bookEntity.getGenre());
        updatedBook.setLanguage(bookEntity.getLanguage());

        return bookRepository.save(updatedBook);
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
