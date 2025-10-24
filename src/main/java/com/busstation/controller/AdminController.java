package com.busstation.controller;

import com.busstation.model.Departure;
import com.busstation.service.DepartureService;
import com.busstation.service.LineService;
import com.busstation.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
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

    @GetMapping("/lines")
    public String lines(Model model) {
        model.addAttribute("lines", lineService.all());
        return "admin/lines";
    }

    @PostMapping("/lines")
    public String createLine(@RequestParam String start,
                             @RequestParam String end) {
        lineService.create(start, end);
        return "redirect:/admin/lines";
    }

    @GetMapping("/departures/{lineId}")
    public String departuresForLine(@PathVariable Long lineId,
                                    @RequestParam(required = false) String startDate,
                                    @RequestParam(required = false) String endDate,
                                    Model model) {
        LocalDate s = startDate != null ? LocalDate.parse(startDate) : LocalDate.now().minusDays(7);
        LocalDate e = endDate != null ? LocalDate.parse(endDate) : LocalDate.now().plusDays(30);
        List<Departure> deps = departureService.forLineInRange(lineId, s, e);
        model.addAttribute("departures", deps);
        model.addAttribute("lineId", lineId);
        return "admin/departures";
    }

    @PostMapping("/departures")
    public String createDeparture(@RequestParam Long lineId,
                                  @RequestParam String date,
                                  @RequestParam String time,
                                  @RequestParam int seats,
                                  @RequestParam String price) {
        departureService.create(lineId, LocalDate.parse(date), LocalTime.parse(time),
                seats, new BigDecimal(price));
        return "redirect:/admin/departures/" + lineId;
    }

    // Jasper hook – prihod za polazak (primer); kasnije ćemo generisati PDF
    @GetMapping("/report/revenue/{departureId}")
    public String revenue(@PathVariable Long departureId, Model model) {
        model.addAttribute("revenue", ticketService.revenueForDeparture(departureId));
        model.addAttribute("departureId", departureId);
        return "admin/revenue"; // JSP koji samo prikaže broj; kasnije zameni Jasper PDF-om
    }
}
