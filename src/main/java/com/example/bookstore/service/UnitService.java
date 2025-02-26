package com.example.bookstore.service;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Library;
import com.example.bookstore.entities.Unit;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.LibraryRepository;
import com.example.bookstore.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Unit> create(Long bookId, Integer numberOfUnits, Unit unitToCreate) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        if (unitToCreate.getId() != null) {
            throw new RuntimeException("You cannot provide an ID to a new unit that you want to create");
        }

        List<Unit> createdUnits = new ArrayList<>();
        for (int i = 1; i <= numberOfUnits; i++) {
            Unit newUnit = new Unit();
            newUnit.setPublisher(unitToCreate.getPublisher());
            newUnit.setMaximumBookingDuration(unitToCreate.getMaximumBookingDuration());
            newUnit.setBook(book);

            unitRepository.save(newUnit);
            createdUnits.add(newUnit);
            book.addUnit(newUnit);
        }

        bookRepository.save(book);

        return createdUnits;
    }

    public Unit getById(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public Page<Unit> findAll(PageRequest pageRequest) {
        return unitRepository.findAll(pageRequest);
    }

    public Unit updateById(Long unitId, Unit unitEntity) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(EntityNotFoundException::new);

        unit.setPublisher(unitEntity.getPublisher());
        unit.setMaximumBookingDuration(unitEntity.getMaximumBookingDuration());

        return unitRepository.save(unit);
    }

    public void deleteById(Long unitId) {
        unitRepository.deleteById(unitId);
    }
}
