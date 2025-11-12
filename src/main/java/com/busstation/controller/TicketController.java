package com.busstation.controller;

import com.busstation.dto.SearchCriteria;
import com.busstation.model.Departure;
import com.busstation.model.User;
import com.busstation.service.DepartureService;
import com.busstation.service.LineService;
import com.busstation.service.TicketService;
import com.busstation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final DepartureService departureService;
    private final TicketService ticketService;
    private final UserService userService;
    private final LineService lineService;

    public TicketController(DepartureService departureService,
                            TicketService ticketService,
                            UserService userService,
                            LineService lineService) {
        this.departureService = departureService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.lineService = lineService;
    }

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        SearchCriteria c = new SearchCriteria();
        c.setDate(LocalDate.now());
        c.setTimeFrom(LocalTime.of(0, 0));
        c.setTimeTo(LocalTime.of(23, 59));

        model.addAttribute("criteria", c);
        model.addAttribute("results", List.of());
        model.addAttribute("startStations", lineService.getAllStartStations());
        model.addAttribute("endStations", lineService.getAllEndStations());

        return "tickets/search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("criteria") @Valid SearchCriteria c,
                         Model model) {
        List<Departure> results = departureService.search(
                c.getDate(),
                c.getStart(),
                c.getEnd(),
                c.getTimeFrom(),
                c.getTimeTo()
        );

        model.addAttribute("criteria", c);
        model.addAttribute("results", results);
        model.addAttribute("startStations", lineService.getAllStartStations());
        model.addAttribute("endStations", lineService.getAllEndStations());

        return "tickets/search";
    }

    @GetMapping("/purchase")
    public String showPurchasePage(@RequestParam Long departureId,
                                   Model model,
                                   Authentication auth) {
        System.out.println("=== PURCHASE PAGE ===");
        System.out.println("Departure ID: " + departureId);
        System.out.println("User authenticated: " + (auth != null));

        try {
            Departure departure = departureService.get(departureId);
            System.out.println("Departure found: " + departure.getLine().getDisplayName());

            model.addAttribute("departure", departure);
            return "tickets/purchase";
        } catch (Exception e) {
            System.err.println("Error loading purchase page: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "redirect:/departures/list";
        }
    }

    @PostMapping("/buy")
    public String buy(@RequestParam Long departureId,
                      @RequestParam int seatCount,
                      Authentication auth,
                      RedirectAttributes redirectAttributes) {
        System.out.println("=== BUY TICKET ===");
        System.out.println("Departure ID: " + departureId);
        System.out.println("Seat count: " + seatCount);

        if (auth == null) {
            System.out.println("User not authenticated!");
            return "redirect:/login";
        }

        try {
            User u = userService.findByUsername(auth.getName()).orElseThrow();
            System.out.println("User: " + u.getUsername());

            ticketService.buy(u.getId(), departureId, seatCount);
            System.out.println("Ticket bought successfully!");

            redirectAttributes.addFlashAttribute("msg", "Uspešno ste kupili " + seatCount + " karte/karata!");
            return "redirect:/tickets/my";
        } catch (Exception e) {
            System.err.println("Error buying ticket: " + e.getMessage());
            e.printStackTrace();

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tickets/purchase?departureId=" + departureId;
        }
    }

    @GetMapping("/my")
    public String myTickets(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }

        User u = userService.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("tickets", ticketService.myTickets(u));
        return "tickets/my";
    }

    @PostMapping("/cancel/{ticketId}")
    public String cancel(@PathVariable Long ticketId,
                         Authentication auth,
                         RedirectAttributes redirectAttributes) {
        System.out.println("=== CANCEL TICKET ===");

        if (auth == null) {
            return "redirect:/login";
        }

        try {
            User u = userService.findByUsername(auth.getName()).orElseThrow();
            ticketService.cancel(ticketId, u.getId());

            redirectAttributes.addFlashAttribute("msg", "Karta je uspešno otkazana!");
            System.out.println("Cancel successful!");
        } catch (Exception e) {
            System.err.println("Cancel error: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/tickets/my";
    }
}