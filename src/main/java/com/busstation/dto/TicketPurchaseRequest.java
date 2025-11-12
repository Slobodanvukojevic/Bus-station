package com.busstation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TicketPurchaseRequest {

    @NotNull
    @Positive
    private Long departureId;

    @NotNull
    @Positive
    private Long userId;

    private int seatCount = 1;

    public Long getDepartureId() { return departureId; }
    public void setDepartureId(Long departureId) { this.departureId = departureId; }


    public int getSeatCount() { return seatCount; }
    public void setSeatCount(int seatCount) {
        this.seatCount = Math.max(1, seatCount);
    }
}
