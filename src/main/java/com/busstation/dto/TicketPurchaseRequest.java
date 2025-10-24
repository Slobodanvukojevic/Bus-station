package com.busstation.dto;

import jakarta.validation.constraints.NotNull;

public class TicketPurchaseRequest {

    @NotNull
    private Long departureId;

    @NotNull
    private Long userId;

    public Long getDepartureId() { return departureId; }
    public void setDepartureId(Long departureId) { this.departureId = departureId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
