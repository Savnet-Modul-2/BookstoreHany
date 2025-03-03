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
    @Query(value = """
            SELECT * FROM exemplary e
            WHERE e.id NOT IN (
                SELECT r.exemplary_id FROM reservation r
                WHERE (r.start_date <= :endDate OR r.end_date >= :startDate)
                AND r.reservation_status IN ('IN_PROGRESS', 'PENDING')
            )
            AND e.book_id = :bookId
            LIMIT 1
            """, nativeQuery = true)
    Optional<Unit> findAvailableUnit(Long bookId, LocalDate startDate, LocalDate endDate);

}
