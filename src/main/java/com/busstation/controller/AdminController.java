package com.busstation.controller;

import com.busstation.model.Departure;
import com.busstation.model.Line;
import com.busstation.service.DepartureService;
import com.busstation.service.LineService;
import com.busstation.service.TicketService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final LineService lineService;
    private final DepartureService departureService;
    private final TicketService ticketService;

    public AdminController(LineService lineService,
                           DepartureService departureService,
                           TicketService ticketService) {
        this.lineService = lineService;
        this.departureService = departureService;
        this.ticketService = ticketService;
    }

    // Administrativna početna stranica
    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("lines", lineService.all());
        return "admin/dashboard";
    }

    // Prikaz svih linija
    @GetMapping("/lines")
    public String listLines(Model model) {
        List<Line> lines = lineService.all();
        model.addAttribute("lines", lines);
        return "admin/lines";
    }

    // Kreiranje nove linije
    @PostMapping("/lines")
    public String createLine(@RequestParam @NotBlank String start,
                             @RequestParam @NotBlank String end) {
        lineService.create(start, end);
        return "redirect:/admin/lines";
    }

    // Brisanje linije
    @PostMapping("/lines/delete/{id}")
    public String deleteLine(@PathVariable Long id) {
        lineService.delete(id);
        return "redirect:/admin/lines";
    }

    // Prikaz polazaka za konkretnu liniju
    @GetMapping("/departures/{lineId}")
    public String listDepartures(@PathVariable Long lineId,
                                 @RequestParam(required = false) String startDate,
                                 @RequestParam(required = false) String endDate,
                                 Model model) {
        LocalDate from = startDate != null ? LocalDate.parse(startDate) : LocalDate.now();
        LocalDate to = endDate != null ? LocalDate.parse(endDate) : LocalDate.now().plusDays(30);

        List<Departure> departures = departureService.forLineInRange(lineId, from, to);
        Line line = lineService.get(lineId);

        model.addAttribute("line", line);
        model.addAttribute("departures", departures);
        model.addAttribute("startDate", from);
        model.addAttribute("endDate", to);
        return "admin/departures";
    }

    // Dodavanje novog polaska
    @PostMapping("/departures")
    public String createDeparture(@RequestParam @NotNull Long lineId,
                                  @RequestParam @NotNull String date,
                                  @RequestParam @NotNull String time,
                                  @RequestParam @Min(1) int seats,
                                  @RequestParam @NotNull String price) {
        departureService.create(
                lineId,
                LocalDate.parse(date),
                LocalTime.parse(time),
                seats,
                new BigDecimal(price)
        );
        return "redirect:/admin/departures/" + lineId;
    }

    // Pregled prihoda za određeni polazak
    @GetMapping("/revenue/{departureId}")
    public String revenueForDeparture(@PathVariable Long departureId, Model model) {
        BigDecimal revenue = ticketService.revenueForDeparture(departureId);
        model.addAttribute("revenue", revenue);
        model.addAttribute("departureId", departureId);
        return "admin/revenue";
    }
}
