package com.busstation.repository;

import com.busstation.model.Departure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DepartureRepository extends JpaRepository<Departure, Long> {

    List<Departure> findByDate(LocalDate date);

    @Query("""
        SELECT d FROM Departure d
        WHERE (:date IS NULL OR d.date = :date)
          AND (:start IS NULL OR LOWER(d.line.startStation) LIKE LOWER(CONCAT('%', :start, '%')))
          AND (:end IS NULL OR LOWER(d.line.endStation) LIKE LOWER(CONCAT('%', :end, '%')))
          AND (:timeFrom IS NULL OR d.time >= :timeFrom)
          AND (:timeTo IS NULL OR d.time <= :timeTo)
        ORDER BY d.date, d.time
    """)
    List<Departure> findByCriteria(@Param("date") LocalDate date,
                                   @Param("start") String start,
                                   @Param("end") String end,
                                   @Param("timeFrom") LocalTime timeFrom,
                                   @Param("timeTo") LocalTime timeTo);

    @Query("""
        SELECT d FROM Departure d
        WHERE d.line.id = :lineId
          AND d.date BETWEEN :startDate AND :endDate
        ORDER BY d.date, d.time
    """)
    List<Departure> findByLineAndDateRange(@Param("lineId") Long lineId,
                                           @Param("startDate") LocalDate start,
                                           @Param("endDate") LocalDate end);
}
