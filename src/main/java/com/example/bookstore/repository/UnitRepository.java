package com.example.bookstore.repository;

import com.example.bookstore.entities.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Page<Unit> findAll(Pageable pageable);
}
