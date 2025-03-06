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
        SELECT * FROM unit
        WHERE unit.id NOT IN (
        SELECT reservation.unit_id FROM reservation
        WHERE ((reservation.start_date <= :endDate AND reservation.end_date >= :startDate)
        AND reservation.status IN ('IN_PROGRESS', 'PENDING'))
        OR reservation.status = 'DELAYED'
        )
        AND unit.book_id = :bookId
        LIMIT 1
    """, nativeQuery = true)
    Optional<Unit> findAvailableUnit(Long bookId, LocalDate startDate, LocalDate endDate);
}
