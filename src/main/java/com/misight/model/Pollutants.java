package com.misight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pollutants")
public class Pollutants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String unit;

    @Column(name = "benchmark_value", nullable = false)
    private Double benchmarkValue;

    @Column(name = "benchmark_type", nullable = false)
    private String benchmarkType;

    @ManyToMany(mappedBy = "pollutants")
    @JsonIgnoreProperties({"pollutants", "province"})
    private Set<MonitoringStations> monitoringStations = new HashSet<>();

    @OneToMany(mappedBy = "pollutant", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pollutant")
    private Set<EnvironmentalData> measurements = new HashSet<>();

    public Pollutants() {}

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getBenchmarkValue() {
        return benchmarkValue;
    }

    public void setBenchmarkValue(Double benchmarkValue) {
        this.benchmarkValue = benchmarkValue;
    }

    public String getBenchmarkType() {
        return benchmarkType;
    }

    public void setBenchmarkType(String benchmarkType) {
        this.benchmarkType = benchmarkType;
    }

    public Set<MonitoringStations> getMonitoringStations() {
        return monitoringStations;
    }

    public void setMonitoringStations(Set<MonitoringStations> monitoringStations) {
        this.monitoringStations = monitoringStations;
    }

    public Set<EnvironmentalData> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<EnvironmentalData> measurements) {
        this.measurements = measurements;
    }
}