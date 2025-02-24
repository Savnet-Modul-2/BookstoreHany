package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Library;
import com.example.bookstore.entities.Unit;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.LibraryRepository;
import com.example.bookstore.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    public Unit create(Unit unit) {
        if (unit.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new application that you want to create.");
        }
        return unitRepository.save(unit);
    }
}
