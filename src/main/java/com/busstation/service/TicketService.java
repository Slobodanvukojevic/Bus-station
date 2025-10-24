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

    public Ticket buy(Long userId, Long departureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Departure dep = departureRepository.findById(departureId)
                .orElseThrow(() -> new IllegalArgumentException("Departure not found"));

        if (ticketRepository.existsActiveForDeparture(user, departureId)) {
            throw new IllegalStateException("User already has an active ticket for this departure");
        }

        departureService.decrementSeat(dep);
        Ticket t = new Ticket(user, dep, dep.getPrice());
        t.setPurchaseDate(LocalDateTime.now());
        return ticketRepository.save(t);
    }

    public List<Ticket> myTickets(User user) {
        return ticketRepository.findByUserOrderByPurchaseDateDesc(user);
    }

    public void cancel(Long ticketId, Long userId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        if (!t.getUser().getId().equals(userId)) {
            throw new SecurityException("Not owner");
        }
        t.setStatus(Ticket.Status.CANCELLED);
        ticketRepository.save(t);
    }

    // Jasper hook – prihod po polasku (primer agregacije)
    public BigDecimal revenueForDeparture(Long departureId) {
        return ticketRepository.sumRevenueByDeparture(departureId);
    }
}
