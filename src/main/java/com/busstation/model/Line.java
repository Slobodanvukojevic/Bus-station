package com.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "bus_lines",
        indexes = {
                @Index(name = "idx_line_from_to", columnList = "startStation,endStation")
        })
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String startStation;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String endStation;

    public Line() {}

    public Line(String startStation, String endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStartStation() { return startStation; }
    public void setStartStation(String startStation) { this.startStation = startStation; }

    public String getEndStation() { return endStation; }
    public void setEndStation(String endStation) { this.endStation = endStation; }

    @Transient
    public String getDisplayName() {
        return startStation + " - " + endStation;
    }
}
