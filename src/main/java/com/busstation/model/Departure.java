package com.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "departures",
        indexes = {
                @Index(name = "idx_departure_date", columnList = "date"),
                @Index(name = "idx_departure_line", columnList = "line_id")
        })
public class Departure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Line line;

    @NotNull @Column(nullable = false)
    private LocalDate date;

    @NotNull @Column(nullable = false)
    private LocalTime time;

    @Min(0) @Column(nullable = false)
    private int availableSeats = 0;

    @NotNull @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    public Departure() { }

    public Departure(Line line, LocalDate date, LocalTime time, int availableSeats, BigDecimal price) {
        this.line = line;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Line getLine() { return line; }
    public void setLine(Line line) { this.line = line; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
