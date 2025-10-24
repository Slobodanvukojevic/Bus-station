package com.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets",
        indexes = {
                @Index(name = "idx_ticket_user", columnList = "user_id"),
                @Index(name = "idx_ticket_departure", columnList = "departure_id"),
                @Index(name = "idx_ticket_status", columnList = "status")
        })
public class Ticket {

    public enum Status { ACTIVE, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Departure departure;

    @NotNull @Column(nullable = false)
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @NotNull @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Status status = Status.ACTIVE;

    public Ticket() { }

    public Ticket(User user, Departure departure, BigDecimal priceAtPurchase) {
        this.user = user;
        this.departure = departure;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Departure getDeparture() { return departure; }
    public void setDeparture(Departure departure) { this.departure = departure; }

    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
