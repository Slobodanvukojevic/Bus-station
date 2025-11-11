package com.busstation.controller;

import com.busstation.model.Departure;
import com.busstation.service.DepartureService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Controller
public class DepartureController {

    private final DepartureService departureService;

    public DepartureController(DepartureService departureService) {
        this.departureService = departureService;
    }

    // /departures/list za list.jsp
    @GetMapping("/departures/list")
    public String listDepartures(Model model) {
        List<Departure> departures = departureService.findAll();
        model.addAttribute("departures", departures);
        return "departures/list";
    }

    // /departures/search za search.jsp (GET forma)
    @GetMapping("/departures/search")
    public String searchDepartures(@RequestParam(required = false) String start,
                                   @RequestParam(required = false) String end,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate date,
                                   Model model) {

        List<Departure> results = Collections.emptyList();

        if (start != null && !start.isBlank()
                && end != null && !end.isBlank()
                && date != null) {
            results = departureService.search(
                    date,
                    start,
                    end,
                    LocalTime.MIN,
                    LocalTime.MAX
            );
        }

        model.addAttribute("results", results);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("date", date);

        return "departures/search";
    }


}