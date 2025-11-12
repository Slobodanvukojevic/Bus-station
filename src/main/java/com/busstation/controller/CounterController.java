package com.busstation.controller;

import com.busstation.model.Ticket;
import com.busstation.model.User;
import com.busstation.service.DepartureService;
import com.busstation.service.TicketService;
import com.busstation.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/counter")
@PreAuthorize("hasAuthority('ROLE_COUNTER')")
public class CounterController {

    private final DepartureService departureService;
    private final TicketService ticketService;
    private final UserService userService;

    public CounterController(DepartureService departureService,
                             TicketService ticketService,
                             UserService userService) {
        this.departureService = departureService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/sales")
    public String showSalesPage(Model model) {
        model.addAttribute("departures", departureService.findAll());
        model.addAttribute("users", userService.findAllUsers());
        return "counter/sales";
    }

    @PostMapping("/sell")
    public String sellTicket(Authentication auth,
                             @RequestParam Long departureId,
                             @RequestParam int seatCount,
                             RedirectAttributes redirectAttributes) {
        System.out.println("=== COUNTER SELL TICKET ===");

        try {
            User counter = userService.findByUsername(auth.getName()).orElseThrow();
            ticketService.sellByCounter(counter.getId(), departureId, seatCount);

            redirectAttributes.addFlashAttribute("msg", "Karta uspešno prodata!");
            System.out.println("Ticket sold by: " + counter.getUsername());
        } catch (Exception e) {
            System.err.println("Error selling ticket: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/counter/sales";
    }

    @GetMapping("/tickets")
    public String viewAllTickets(@RequestParam(required = false, defaultValue = "date_desc") String sort,
                                 Model model) {
        System.out.println("=== VIEW ALL TICKETS (COUNTER) ===");
        System.out.println("Sort by: " + sort);

        List<Ticket> allTickets = ticketService.getAllTickets();

        // SORTIRANJE
        allTickets = sortTickets(allTickets, sort);

        model.addAttribute("tickets", allTickets);
        model.addAttribute("currentSort", sort);

        System.out.println("Total tickets: " + allTickets.size());
        return "counter/tickets";
    }


    @GetMapping("/tickets/user")
    public String viewUserTickets(@RequestParam Long userId,
                                  @RequestParam(required = false, defaultValue = "date_desc") String sort,
                                  Model model) {
        System.out.println("=== VIEW USER TICKETS (COUNTER) ===");
        System.out.println("User ID: " + userId);
        System.out.println("Sort by: " + sort);

        try {
            User user = userService.findAllUsers().stream()
                    .filter(u -> u.getId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen"));

            List<Ticket> userTickets = ticketService.myTickets(user);

            // Sortiranje
            userTickets = sortTickets(userTickets, sort);

            model.addAttribute("tickets", userTickets);
            model.addAttribute("selectedUser", user);
            model.addAttribute("currentSort", sort);

            System.out.println("User tickets: " + userTickets.size());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }

        return "counter/tickets";
    }


    @PostMapping("/tickets/cancel/{ticketId}")
    public String cancelTicket(@PathVariable Long ticketId,
                               Authentication auth,
                               RedirectAttributes redirectAttributes) {
        System.out.println("=== COUNTER CANCEL TICKET ===");
        System.out.println("Ticket ID: " + ticketId);

        try {
            // Counter može otkazati bilo čiju kartu, ali koristimo counter ID
            User counter = userService.findByUsername(auth.getName()).orElseThrow();

            // Posebna metoda za counter otkazivanje
            ticketService.cancelByCounter(ticketId);

            redirectAttributes.addFlashAttribute("msg", "Karta je uspešno otkazana!");
            System.out.println("Ticket cancelled by counter: " + counter.getUsername());
        } catch (Exception e) {
            System.err.println("Error cancelling ticket: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/counter/tickets";
    }


    private List<Ticket> sortTickets(List<Ticket> tickets, String sort) {
        return switch (sort) {
            case "date_asc" -> tickets.stream()
                    .sorted(Comparator.comparing(Ticket::getPurchaseDate))
                    .collect(Collectors.toList());
            case "date_desc" -> tickets.stream()
                    .sorted(Comparator.comparing(Ticket::getPurchaseDate).reversed())
                    .collect(Collectors.toList());
            case "seats_asc" -> tickets.stream()
                    .sorted(Comparator.comparing(Ticket::getSeatCount))
                    .collect(Collectors.toList());
            case "seats_desc" -> tickets.stream()
                    .sorted(Comparator.comparing(Ticket::getSeatCount).reversed())
                    .collect(Collectors.toList());
            default -> tickets.stream()
                    .sorted(Comparator.comparing(Ticket::getPurchaseDate).reversed())
                    .collect(Collectors.toList());
        };
    }
}