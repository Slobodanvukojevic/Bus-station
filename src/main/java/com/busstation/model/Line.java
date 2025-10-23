package com.busstation.model;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lines")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String startStation;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String endStation;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    private Set<Departure> departures = new HashSet<>();

    public Line() {}

    public Line(String name, String startStation, String endStation) {
        this.name = name;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStartStation() { return startStation; }
    public void setStartStation(String startStation) { this.startStation = startStation; }
    public String getEndStation() { return endStation; }
    public void setEndStation(String endStation) { this.endStation = endStation; }
    public Set<Departure> getDepartures() { return departures; }
    public void setDepartures(Set<Departure> departures) { this.departures = departures; }
}