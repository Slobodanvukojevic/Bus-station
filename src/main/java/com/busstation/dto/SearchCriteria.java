package com.busstation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class SearchCriteria {

    @NotBlank
    private String start;

    @NotBlank
    private String end;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime timeFrom;

    @NotNull
    private LocalTime timeTo;

    public String getStart() { return start; }
    public void setStart(String start) { this.start = start; }

    public String getEnd() { return end; }
    public void setEnd(String end) { this.end = end; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTimeFrom() { return timeFrom; }
    public void setTimeFrom(LocalTime timeFrom) { this.timeFrom = timeFrom; }

    public LocalTime getTimeTo() { return timeTo; }
    public void setTimeTo(LocalTime timeTo) { this.timeTo = timeTo; }
}
