package com.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Line line;

    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @Column(nullable = false)
    private LocalTime time;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String driver;

    @Min(1)
    @Column(nullable = false)
    private int capacity;

    @Min(0)
    @Column(nullable = false)
    private int availableSeats;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Version
    private int version;

    public Departure() {}

    public Departure(Line line, LocalDate date, LocalTime time, String driver, int capacity, BigDecimal price) {
        this.line = line;
        this.date = date;
        this.time = time;
        this.driver = driver;
        this.capacity = capacity;
        this.availableSeats = capacity;
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

    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}
