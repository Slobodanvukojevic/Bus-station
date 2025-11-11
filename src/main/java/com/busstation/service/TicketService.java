package com.busstation.service;

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

        if (ticketRepository.existsActiveForDeparture(user, departureId)) {
            throw new IllegalStateException("User already has an active ticket for this departure");
        }

// provera kapaciteta polaska
        if (dep.getAvailableSeats() < seatCount) {
            throw new IllegalStateException("Nema dovoljno slobodnih sedišta.");
        }

// smanji broj slobodnih sedišta
        departureService.decrementSeats(dep, seatCount);

// napravi kartu
        Ticket t = new Ticket(user, dep, seatCount, dep.getPrice());
        t.setPurchaseDate(LocalDateTime.now());
        return ticketRepository.save(t);
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

    public void cancel(Long ticketId, Long userId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Karta nije pronađena."));

        if (!t.getUser().getId().equals(userId)) {
            throw new SecurityException("Nemate pravo da otkažete ovu kartu.");
        }

        // zabrana otkazivanja manje od 24h pre polaska
        LocalDateTime departureTime = LocalDateTime.of(
                t.getDeparture().getDate(),
                t.getDeparture().getTime()
        );
        if (LocalDateTime.now().isAfter(departureTime.minusHours(24))) {
            throw new IllegalStateException("Kartu nije moguće otkazati manje od 24h pre polaska.");
        }

        t.setStatus(Ticket.Status.CANCELLED);
        ticketRepository.save(t);
    }

    public BigDecimal revenueForDeparture(Long departureId) {
        return ticketRepository.sumRevenueByDeparture(departureId);
    }
}
