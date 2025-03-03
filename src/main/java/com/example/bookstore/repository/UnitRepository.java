package com.example.bookstore.repository;

import com.example.bookstore.entities.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Page<Unit> getById(Long bookId, Pageable pageable);
    void deleteById(Long unitId);

    @Query(value = """
        SELECT * FROM unit unit
        WHERE unit.book_id = :bookId
        AND unit.id NOT IN (
            SELECT reservation.unit_id FROM reservation reservation
            WHERE reservation.unit_id = unit.id
            AND NOT (reservation.end_date < :startDate OR reservation.start_date > :endDate)
        )
        LIMIT 1
    """, nativeQuery = true)
    Optional<Unit> findAvailableUnit(Long bookId, LocalDate startDate, LocalDate endDate);
}
