package com.busstation.dto;


import java.math.BigDecimal;

public class TicketReportDTO {
    private String ticketCode;
    private String username;
    private Integer seatCount;
    private BigDecimal priceAtPurchase;
    private String purchaseDate;

    public TicketReportDTO(String ticketCode, String username, Integer seatCount,
                           BigDecimal priceAtPurchase, String purchaseDate) {
        this.ticketCode = ticketCode;
        this.username = username;
        this.seatCount = seatCount;
        this.priceAtPurchase = priceAtPurchase;
        this.purchaseDate = purchaseDate;
    }

    // Getters and setters
    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getSeatCount() { return seatCount; }
    public void setSeatCount(Integer seatCount) { this.seatCount = seatCount; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
}