package com.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets",
        indexes = {
                @Index(name = "idx_ticket_user", columnList = "user_id"),
                @Index(name = "idx_ticket_departure", columnList = "departure_id"),
                @Index(name = "idx_ticket_status", columnList = "status"),
                @Index(name = "idx_ticket_code", columnList = "ticketCode", unique = true)
        })
public class Ticket {


    public Ticket() {

    }

    public enum Status { ACTIVE, CANCELLED, USED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Departure departure;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String ticketCode;

    @Min(1)
    @Column(nullable = false)
    private int seatCount;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Status status = Status.ACTIVE;

    public Ticket(User user, Departure dep, BigDecimal price) {}

    public Ticket(User user, Departure departure, int seatCount, BigDecimal priceAtPurchase) {
        this.user = user;
        this.departure = departure;
        this.seatCount = seatCount;
        this.priceAtPurchase = priceAtPurchase;
        this.ticketCode = UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Departure getDeparture() { return departure; }
    public void setDeparture(Departure departure) { this.departure = departure; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public int getSeatCount() { return seatCount; }
    public void setSeatCount(int seatCount) { this.seatCount = seatCount; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
