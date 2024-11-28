package com.misight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "monitoring_stations")
public class MonitoringStations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    @JsonIgnoreProperties({"monitoringStations", "mines"})
    private Provinces province;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "station_pollutants",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "pollutant_id")
    )
    @JsonIgnoreProperties("monitoringStations")
    private Set<Pollutants> pollutants = new HashSet<>();

    @OneToMany(mappedBy = "monitoringStation", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("monitoringStation")
    private Set<EnvironmentalData> measurements = new HashSet<>();

    public MonitoringStations() {}

    public MonitoringStations(String location, Provinces province) {
        this.location = location;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Provinces getProvince() {
        return province;
    }

    public void setProvince(Provinces province) {
        this.province = province;
    }

    public Set<Pollutants> getPollutants() {
        return pollutants;
    }

    public void setPollutants(Set<Pollutants> pollutants) {
        this.pollutants = pollutants;
    }

    public Set<EnvironmentalData> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<EnvironmentalData> measurements) {
        this.measurements = measurements;
    }
}
