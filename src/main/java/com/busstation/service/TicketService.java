package com.busstation.service;

import com.busstation.dto.TicketReportDTO;
import com.busstation.model.Departure;
import com.busstation.model.Ticket;
import com.busstation.model.User;
import com.busstation.repository.DepartureRepository;
import com.busstation.repository.TicketRepository;
import com.busstation.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final DepartureRepository departureRepository;
    private final DepartureService departureService;

    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         DepartureRepository departureRepository,
                         DepartureService departureService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.departureRepository = departureRepository;
        this.departureService = departureService;
    }

    public Ticket buy(Long userId, Long departureId, int seatCount) {
        if (seatCount <= 0) {
            throw new IllegalArgumentException("Broj sedišta mora biti najmanje 1.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Departure dep = departureRepository.findById(departureId)
                .orElseThrow(() -> new IllegalArgumentException("Departure not found"));



        if (dep.getAvailableSeats() < seatCount) {
            throw new IllegalStateException("Nema dovoljno slobodnih sedišta.");
        }

        departureService.decrementSeats(dep, seatCount);

        Ticket t = new Ticket(user, dep, seatCount, dep.getPrice());
        t.setPurchaseDate(LocalDateTime.now());
        return ticketRepository.save(t);
    }

    public long countTicketsForDeparture(Long departureId) {
        return ticketRepository.countActiveSeatsByDeparture(departureId);
    }
    public List<TicketReportDTO> getTicketsForReport(Long departureId) {
        return ticketRepository.findTicketsForReport(departureId);
    }

    @Transactional
    public Ticket sellByCounter(Long counterId, Long departureId, int seatCount) {
        // Radnik je upisan kao "user_id"
        return buy(counterId, departureId, seatCount);
    }

    public List<Ticket> myTickets(User user) {
        // vraća sve karte za korisnika, najnovije prve
        return ticketRepository.findByUserOrderByPurchaseDateDesc(user);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll().stream()
                .sorted((t1, t2) -> t2.getPurchaseDate().compareTo(t1.getPurchaseDate()))
                .collect(Collectors.toList());
    }

    public void cancel(Long ticketId, Long userId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Karta nije pronađena."));

        if (!t.getUser().getId().equals(userId)) {
            throw new SecurityException("Nemate pravo da otkažete ovu kartu.");
        }

        Departure departure = t.getDeparture();
        departure.setAvailableSeats(departure.getAvailableSeats() + t.getSeatCount());
        departureRepository.save(departure);

        System.out.println("Returned " + t.getSeatCount() + " seats. New available: " + departure.getAvailableSeats());



        t.setStatus(Ticket.Status.CANCELLED);
        ticketRepository.save(t);
    }

    @Transactional
    public void cancelByCounter(Long ticketId) {
        System.out.println("=== TicketService.cancelByCounter ===");
        System.out.println("Ticket ID: " + ticketId);

        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Karta nije pronađena."));

        if (t.getStatus() != Ticket.Status.ACTIVE) {
            throw new IllegalStateException("Karta je već otkazana ili iskorišćena.");
        }

        Departure departure = t.getDeparture();
        departure.setAvailableSeats(departure.getAvailableSeats() + t.getSeatCount());
        departureRepository.save(departure);

        System.out.println("Returned " + t.getSeatCount() + " seats. New available: " + departure.getAvailableSeats());

        t.setStatus(Ticket.Status.CANCELLED);
        ticketRepository.save(t);

        System.out.println("Ticket cancelled by counter!");
    }

    public BigDecimal revenueForDeparture(Long departureId) {
        return ticketRepository.sumRevenueByDeparture(departureId);
    }
}
