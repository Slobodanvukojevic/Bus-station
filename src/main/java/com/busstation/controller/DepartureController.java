package com.busstation.controller;

import com.busstation.model.Departure;
import com.busstation.service.DepartureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DepartureController {

    private final DepartureService departureService;

    public DepartureController(DepartureService departureService) {
        this.departureService = departureService;
    }


    @GetMapping("/departures/list")
    public String listDepartures(Model model) {
        List<Departure> departures = departureService.findAll();
        model.addAttribute("departures", departures);
        return "departures/list";
    }




}