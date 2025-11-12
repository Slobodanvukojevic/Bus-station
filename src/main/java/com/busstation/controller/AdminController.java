package com.busstation.controller;

import com.busstation.dto.TicketReportDTO;
import com.busstation.model.Departure;
import com.busstation.model.Line;
import com.busstation.model.User;
import com.busstation.service.DepartureService;
import com.busstation.service.LineService;
import com.busstation.service.TicketService;
import com.busstation.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LineService lineService;
    private final DepartureService departureService;
    private final TicketService ticketService;
    private final UserService userService;  // DODAJ OVO

    public AdminController(LineService lineService,
                           DepartureService departureService,
                           TicketService ticketService,
                           UserService userService) {  // DODAJ OVO
        this.lineService = lineService;
        this.departureService = departureService;
        this.ticketService = ticketService;
        this.userService = userService;  // DODAJ OVO
    }

    // Admin dashboard
    @GetMapping
    public String adminHome(Model model) {
        System.out.println("=== ADMIN DASHBOARD ===");
        model.addAttribute("lines", lineService.all());
        return "admin/dashboard";
    }

    // List all lines
    @GetMapping("/lines")
    public String listLines(Model model) {
        System.out.println("=== LIST LINES ===");
        List<Line> lines = lineService.all();
        model.addAttribute("lines", lines);
        return "admin/lines";
    }

    // Create new line
    @PostMapping("/lines")
    public String createLine(@RequestParam @NotBlank String start,
                             @RequestParam @NotBlank String end,
                             Model model) {
        System.out.println("=== CREATE LINE ===");
        System.out.println("Start: " + start);
        System.out.println("End: " + end);

        try {
            lineService.create(start, end);
            System.out.println("Line created successfully!");
            model.addAttribute("msg", "Linija uspešno kreirana!");
        } catch (Exception e) {
            System.err.println("Error creating line: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Greška: " + e.getMessage());
        }

        return "redirect:/admin/lines";
    }

    // Delete line
    @PostMapping("/lines/delete/{id}")
    public String deleteLine(@PathVariable Long id, Model model) {
        System.out.println("=== DELETE LINE ===");
        System.out.println("Line ID: " + id);

        try {
            lineService.delete(id);
            System.out.println("Line deleted successfully!");
            model.addAttribute("msg", "Linija uspešno obrisana!");
        } catch (Exception e) {
            System.err.println("Error deleting line: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Greška: " + e.getMessage());
        }

        return "redirect:/admin/lines";
    }

    // List departures for a line
    @GetMapping("/departures/{lineId}")
    public String listDepartures(@PathVariable Long lineId,
                                 @RequestParam(required = false) String startDate,
                                 @RequestParam(required = false) String endDate,
                                 Model model) {
        System.out.println("=== LIST DEPARTURES ===");
        System.out.println("Line ID: " + lineId);

        try {
            LocalDate from = startDate != null ? LocalDate.parse(startDate) : LocalDate.now();
            LocalDate to = endDate != null ? LocalDate.parse(endDate) : LocalDate.now().plusDays(30);

            List<Departure> departures = departureService.forLineInRange(lineId, from, to);
            Line line = lineService.get(lineId);

            model.addAttribute("line", line);
            model.addAttribute("departures", departures);
            model.addAttribute("startDate", from);
            model.addAttribute("endDate", to);

            System.out.println("Found " + departures.size() + " departures");
        } catch (Exception e) {
            System.err.println("Error listing departures: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Greška: " + e.getMessage());
        }

        return "admin/departures";
    }

    // Create new departure
    @PostMapping("/departures")
    public String createDeparture(@RequestParam @NotNull Long lineId,
                                  @RequestParam @NotNull String date,
                                  @RequestParam @NotNull String time,
                                  @RequestParam @NotBlank String driver,
                                  @RequestParam @Min(1) int seats,
                                  @RequestParam @NotNull String price,
                                  Model model) {
        System.out.println("=== CREATE DEPARTURE ===");
        System.out.println("Line ID: " + lineId);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Driver: " + driver);
        System.out.println("Seats: " + seats);
        System.out.println("Price: " + price);

        try {
            departureService.create(
                    lineId,
                    LocalDate.parse(date),
                    LocalTime.parse(time),
                    driver,
                    seats,
                    new BigDecimal(price)
            );
            System.out.println("Departure created successfully!");
            model.addAttribute("msg", "Polazak uspešno kreiran!");
        } catch (Exception e) {
            System.err.println("Error creating departure: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Greška: " + e.getMessage());
        }

        return "redirect:/admin/departures/" + lineId;
    }

    // List all users
    @GetMapping("/users")
    public String listUsers(Model model) {
        System.out.println("=== LIST USERS ===");
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    // Delete departure
    // Delete departure
    @PostMapping("/departures/delete/{id}")
    public String deleteDeparture(@PathVariable Long id, Model model) {
        System.out.println("=== DELETE DEPARTURE ===");
        System.out.println("Departure ID: " + id);

        try {
            Departure dep = departureService.get(id);
            Long lineId = dep.getLine().getId();

            // Proveri da li ima karata
            long ticketCount = ticketService.countTicketsForDeparture(id);
            if (ticketCount > 0) {
                model.addAttribute("error", "Ne možete obrisati polazak koji ima " + ticketCount + " prodatih karata!");
                return "redirect:/admin/departures/" + lineId;
            }

            departureService.delete(id);
            System.out.println("Departure deleted successfully!");
            model.addAttribute("msg", "Polazak uspešno obrisan!");

            return "redirect:/admin/departures/" + lineId;
        } catch (Exception e) {
            System.err.println("Error deleting departure: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Greška: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    // Revenue for departure
    @GetMapping("/revenue/{departureId}")
    public String revenueForDeparture(@PathVariable Long departureId, Model model) {
        System.out.println("=== REVENUE FOR DEPARTURE ===");
        System.out.println("Departure ID: " + departureId);

        try {
            BigDecimal revenue = ticketService.revenueForDeparture(departureId);
            model.addAttribute("revenue", revenue);
            model.addAttribute("departureId", departureId);
            System.out.println("Revenue: " + revenue);
        } catch (Exception e) {
            System.err.println("Error calculating revenue: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Greška: " + e.getMessage());
        }

        return "admin/revenue";
    }

    @GetMapping("/revenue/{departureId}/pdf")
    public void generateRevenuePDF(@PathVariable Long departureId, HttpServletResponse response) {
        System.out.println("=== GENERATE REVENUE PDF ===");

        try {
            Departure departure = departureService.get(departureId);
            BigDecimal revenue = ticketService.revenueForDeparture(departureId);
            List<TicketReportDTO> tickets = ticketService.getTicketsForReport(departureId);

            // Load JRXML
            InputStream jrxmlInput = getClass().getResourceAsStream("/reports/revenue_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInput);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("departureId", departureId);
            parameters.put("lineInfo", departure.getLine().getDisplayName());
            parameters.put("departureDate", departure.getDate().toString());
            parameters.put("departureTime", departure.getTime().toString());
            parameters.put("totalRevenue", revenue);

            // Data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tickets);

            // Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export to PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=revenue_report_" + departureId + ".pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            System.out.println("PDF generated successfully!");
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}