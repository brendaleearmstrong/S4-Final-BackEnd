package com.misight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "environmental_data")
public class EnvironmentalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pollutant_id")
    @JsonIgnoreProperties({"measurements", "monitoringStations"})
    private Pollutants pollutant;

    @ManyToOne
    @JoinColumn(name = "station_id")
    @JsonIgnoreProperties({"measurements", "pollutants", "province"})
    private MonitoringStations monitoringStation;

    @ManyToOne
    @JoinColumn(name = "mine_id")
    @JsonIgnoreProperties({"environmentalData", "safetyData", "minerals", "province"})
    private Mines mine;

    @Column(nullable = false)
    private Double measuredValue;

    @Column(nullable = false)
    private LocalDateTime measurementDate;

    private String notes;

    public EnvironmentalData() {}

    public EnvironmentalData(Pollutants pollutant, MonitoringStations monitoringStation,
                             Mines mine, Double measuredValue, LocalDateTime measurementDate) {
        this.pollutant = pollutant;
        this.monitoringStation = monitoringStation;
        this.mine = mine;
        this.measuredValue = measuredValue;
        this.measurementDate = measurementDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pollutants getPollutant() {
        return pollutant;
    }

    public void setPollutant(Pollutants pollutant) {
        this.pollutant = pollutant;
    }

    public MonitoringStations getMonitoringStation() {
        return monitoringStation;
    }

    public void setMonitoringStation(MonitoringStations monitoringStation) {
        this.monitoringStation = monitoringStation;
    }

    public Mines getMine() {
        return mine;
    }

    public void setMine(Mines mine) {
        this.mine = mine;
    }

    public Double getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(Double measuredValue) {
        this.measuredValue = measuredValue;
    }

    public LocalDateTime getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDateTime measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}