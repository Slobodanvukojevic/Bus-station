package com.busstation.service;

import com.busstation.model.Departure;
import com.busstation.model.Line;
import com.busstation.repository.DepartureRepository;
import com.busstation.repository.LineRepository;
import com.busstation.repository.TicketRepository;
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
    private final TicketRepository ticketRepository;

    public DepartureService(DepartureRepository departureRepository,
                            LineRepository lineRepository, TicketRepository ticketRepository) {
        this.departureRepository = departureRepository;
        this.lineRepository = lineRepository;
        this.ticketRepository = ticketRepository;
    }

    public Departure create(Long lineId, LocalDate date, LocalTime time, String driver, int seats, BigDecimal price) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("Line not found"));

        Departure departure = new Departure(line, date, time, driver, seats, price);
        return departureRepository.save(departure);
    }

    public void delete(Long id) {
        System.out.println("=== DepartureService.delete ===");
        System.out.println("Deleting departure: " + id);

        Departure departure = departureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departure not found"));

        departureRepository.delete(departure);
        System.out.println("Departure deleted successfully!");
    }

    public long countTicketsForDeparture(Long departureId) {

        return ticketRepository.countActiveSeatsByDeparture(departureId);
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

    public void decrementSeats(Departure d, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Broj sedišta mora biti najmanje 1.");
        }
        if (d.getAvailableSeats() < count) {
            throw new IllegalStateException("Nema dovoljno slobodnih mesta.");
        }
        d.setAvailableSeats(d.getAvailableSeats() - count);
        departureRepository.save(d);
    }
    public List<Departure> findAll() {
        return departureRepository.findAll();
    }

}
