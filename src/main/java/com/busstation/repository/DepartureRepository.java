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
        WHERE d.date = :date
          AND d.line.startStation = :start
          AND d.line.endStation = :end
          AND d.time BETWEEN :timeFrom AND :timeTo
        ORDER BY d.time
    """)
    List<Departure> findByCriteria(@Param("date") LocalDate date,
                                   @Param("start") String start,
                                   @Param("end") String end,
                                   @Param("timeFrom") LocalTime timeFrom,
                                   @Param("timeTo") LocalTime timeTo);

    @Query("""
        SELECT d FROM Departure d
        WHERE d.line.id = :lineId
          AND d.date BETWEEN :start AND :end
        ORDER BY d.date, d.time
    """)
    List<Departure> findByLineAndDateRange(@Param("lineId") Long lineId,
                                           @Param("start") LocalDate start,
                                           @Param("end") LocalDate end);
}
