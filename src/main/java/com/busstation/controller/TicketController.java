package com.busstation.controller;

import com.busstation.dto.SearchCriteria;
import com.busstation.model.Departure;
import com.busstation.model.Ticket;
import com.busstation.model.User;
import com.busstation.service.DepartureService;
import com.busstation.service.TicketService;
import com.busstation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
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

    @GetMapping("/departures")
    public String departuresForm(Model model) {
        SearchCriteria c = new SearchCriteria();
        c.setDate(LocalDate.now());
        c.setTimeFrom(LocalTime.of(0,0));
        c.setTimeTo(LocalTime.of(23,59));
        model.addAttribute("criteria", c);
        model.addAttribute("results", List.of());
        return "departures";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("criteria") @Valid SearchCriteria c,
                         Model model) {
        List<Departure> results = departureService.search(c.getDate(), c.getStart(), c.getEnd(), c.getTimeFrom(), c.getTimeTo());
        model.addAttribute("criteria", c);
        model.addAttribute("results", results);
        return "departures";
    }

    @PostMapping("/buy/{departureId}")
    public String buy(@PathVariable Long departureId,
                      Authentication auth,
                      Model model) {
        if (auth == null) {
            model.addAttribute("error", "Morate biti prijavljeni.");
            return "login";
        }
        User u = userService.findByUsername(auth.getName()).orElseThrow();
        try {
            Ticket t = ticketService.buy(u.getId(), departureId);
            model.addAttribute("msg", "Kupljena karta #" + t.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/my-tickets";
    }

    @GetMapping("/my-tickets")
    public String myTickets(Authentication auth, Model model) {
        if (auth == null) return "login";
        User u = userService.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("tickets", ticketService.myTickets(u));
        return "my-tickets";
    }

    @PostMapping("/cancel/{ticketId}")
    public String cancel(@PathVariable Long ticketId, Authentication auth) {
        if (auth != null) {
            User u = userService.findByUsername(auth.getName()).orElseThrow();
            ticketService.cancel(ticketId, u.getId());
        }
        return "redirect:/my-tickets";
    }
}
