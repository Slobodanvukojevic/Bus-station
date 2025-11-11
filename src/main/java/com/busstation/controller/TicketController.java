package com.busstation.controller;

import com.busstation.dto.SearchCriteria;
import com.busstation.dto.TicketPurchaseRequest;
import com.busstation.model.Departure;
import com.busstation.model.User;
import com.busstation.service.DepartureService;
import com.busstation.service.TicketService;
import com.busstation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final DepartureService departureService;
    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(DepartureService departureService,
                            TicketService ticketService,
                            UserService userService) {
        this.departureService = departureService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        SearchCriteria c = new SearchCriteria();
        c.setDate(LocalDate.now());
        c.setTimeFrom(LocalTime.of(0, 0));
        c.setTimeTo(LocalTime.of(23, 59));
        model.addAttribute("criteria", c);
        model.addAttribute("results", List.of());
        return "tickets/search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("criteria") @Valid SearchCriteria c,
                         Model model) {
        List<Departure> results = departureService.search(c.getDate(), c.getStart(), c.getEnd(), c.getTimeFrom(), c.getTimeTo());
        model.addAttribute("criteria", c);
        model.addAttribute("results", results);
        return "tickets/search";
    }

    @PostMapping("/buy")
    public String buy(@ModelAttribute @Valid TicketPurchaseRequest request,
                      Authentication auth,
                      Model model) {
        if (auth == null) {
            model.addAttribute("error", "Morate biti prijavljeni.");
            return "login";
        }

        User u = userService.findByUsername(auth.getName()).orElseThrow();
        try {
            ticketService.buy(u.getId(), request.getDepartureId(), request.getSeatCount());
            model.addAttribute("msg", "Uspešno ste kupili kartu.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/tickets/my";
    }

    @GetMapping("/counter/sales")
    @PreAuthorize("hasRole('ROLE_COUNTER')")
    public String showSalesPage(Model model) {
        model.addAttribute("departures", departureService.findAll());
        return "counter/sales";
    }

    @PostMapping("/counter/sell")
    @PreAuthorize("hasRole('ROLE_COUNTER')")
    public String sellTicket(@RequestParam Long userId,
                             @RequestParam Long departureId,
                             Model model) {
        try {
            ticketService.buy(userId, departureId, 1);
            model.addAttribute("msg", "Karta je uspešno prodata korisniku ID: " + userId);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/counter/sales";
    }

    @GetMapping("/my")
    public String myTickets(Authentication auth, Model model) {
        if (auth == null) return "login";
        User u = userService.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("tickets", ticketService.myTickets(u));
        return "tickets/my";
    }

    @PostMapping("/cancel/{ticketId}")
    public String cancel(@PathVariable Long ticketId, Authentication auth) {
        if (auth != null) {
            User u = userService.findByUsername(auth.getName()).orElseThrow();
            ticketService.cancel(ticketId, u.getId());
        }
        return "redirect:/tickets/my";
    }
}
