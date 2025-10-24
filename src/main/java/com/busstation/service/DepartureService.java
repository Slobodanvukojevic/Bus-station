package com.busstation.service;

import com.busstation.model.Departure;
import com.busstation.model.Line;
import com.busstation.repository.DepartureRepository;
import com.busstation.repository.LineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class DepartureService {

    private final DepartureRepository departureRepository;
    private final LineRepository lineRepository;

    public DepartureService(DepartureRepository departureRepository,
                            LineRepository lineRepository) {
        this.departureRepository = departureRepository;
        this.lineRepository = lineRepository;
    }

    public Departure create(Long lineId, LocalDate date, LocalTime time, int seats, BigDecimal price) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("Line not found"));
        return departureRepository.save(new Departure(line, date, time, seats, price));
    }

    public List<Departure> search(LocalDate date, String start, String end, LocalTime from, LocalTime to) {
        return departureRepository.findByCriteria(date, start, end, from, to);
    }

    public Departure get(Long id) {
        return departureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departure not found"));
    }

    public List<Departure> forLineInRange(Long lineId, LocalDate start, LocalDate end) {
        return departureRepository.findByLineAndDateRange(lineId, start, end);
    }

    public void decrementSeat(Departure d) {
        if (d.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No seats available");
        }
        d.setAvailableSeats(d.getAvailableSeats() - 1);
        departureRepository.save(d);
    }
}
