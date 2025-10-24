package com.busstation.repository;

import com.busstation.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {
    List<Line> findByStartStationAndEndStation(String startStation, String endStation);
}
