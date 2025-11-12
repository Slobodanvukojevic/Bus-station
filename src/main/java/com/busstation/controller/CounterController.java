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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    // Prikaz prodajnog ekrana za radnika
    @GetMapping("/sales")
    public String showSalesPage(Model model) {
        model.addAttribute("departures", departureService.findAll());
        model.addAttribute("users", userService.findAllUsers());
        return "counter/sales";
    }

    // Akcija prodaje karte korisniku
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

    // Pregled svih prodatih karata
    @GetMapping("/tickets")
    public String viewAllTickets(Model model) {
        System.out.println("=== VIEW ALL TICKETS (COUNTER) ===");

        List<Ticket> allTickets = ticketService.getAllTickets();
        model.addAttribute("tickets", allTickets);

        System.out.println("Total tickets: " + allTickets.size());
        return "counter/tickets";
    }

    // Pregled karata za određenog korisnika
    @GetMapping("/tickets/user")
    public String viewUserTickets(@RequestParam Long userId, Model model) {
        System.out.println("=== VIEW USER TICKETS (COUNTER) ===");
        System.out.println("User ID: " + userId);

        try {
            User user = userService.findAllUsers().stream()
                    .filter(u -> u.getId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen"));

            List<Ticket> userTickets = ticketService.myTickets(user);

            model.addAttribute("tickets", userTickets);
            model.addAttribute("selectedUser", user);

            System.out.println("User tickets: " + userTickets.size());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }

        return "counter/tickets";
    }
}