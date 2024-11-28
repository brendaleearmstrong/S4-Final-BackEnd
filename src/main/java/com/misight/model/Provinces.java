package com.misight.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "provinces")
public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private Set<Mines> mines = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private Set<MonitoringStations> monitoringStations = new HashSet<>();

    public Provinces() {}

    public Provinces(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Mines> getMines() {
        return mines;
    }

    public void setMines(Set<Mines> mines) {
        this.mines = mines;
    }

    @JsonIgnore
    public Set<MonitoringStations> getMonitoringStations() {
        return monitoringStations;
    }

    public void setMonitoringStations(Set<MonitoringStations> stations) {
        this.monitoringStations = stations;
    }
}