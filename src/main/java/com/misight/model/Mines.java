package com.misight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mines")
public class Mines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id", nullable = false)
    @JsonIgnoreProperties({"mines", "monitoringStations"})
    private Provinces province;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mine_minerals",
            joinColumns = @JoinColumn(name = "mine_id"),
            inverseJoinColumns = @JoinColumn(name = "mineral_id")
    )
    @JsonIgnoreProperties("mines")
    private Set<Minerals> minerals = new HashSet<>();

    @OneToMany(mappedBy = "mine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("mine")
    private Set<SafetyData> safetyData = new HashSet<>();

    @OneToMany(mappedBy = "mine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("mine")
    private Set<EnvironmentalData> environmentalData = new HashSet<>();

    public Mines() {}

    public Mines(String name, String company, String location, Provinces province) {
        this.name = name;
        this.company = company;
        this.location = location;
        this.province = province;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Set<Minerals> getMinerals() {
        return minerals;
    }

    public void setMinerals(Set<Minerals> minerals) {
        this.minerals.clear();
        if (minerals != null) {
            this.minerals.addAll(minerals);
        }
    }

    public Set<SafetyData> getSafetyData() {
        return safetyData;
    }

    public void setSafetyData(Set<SafetyData> safetyData) {
        this.safetyData.clear();
        if (safetyData != null) {
            this.safetyData.addAll(safetyData);
            this.safetyData.forEach(data -> data.setMine(this));
        }
    }

    public Set<EnvironmentalData> getEnvironmentalData() {
        return environmentalData;
    }

    public void setEnvironmentalData(Set<EnvironmentalData> environmentalData) {
        this.environmentalData.clear();
        if (environmentalData != null) {
            this.environmentalData.addAll(environmentalData);
            this.environmentalData.forEach(data -> data.setMine(this));
        }
    }

    public void addMineral(Minerals mineral) {
        minerals.add(mineral);
        mineral.getMines().add(this);
    }

    public void removeMineral(Minerals mineral) {
        minerals.remove(mineral);
        mineral.getMines().remove(this);
    }

    public void addSafetyData(SafetyData data) {
        safetyData.add(data);
        data.setMine(this);
    }

    public void removeSafetyData(SafetyData data) {
        safetyData.remove(data);
        data.setMine(null);
    }

    public void addEnvironmentalData(EnvironmentalData data) {
        environmentalData.add(data);
        data.setMine(this);
    }

    public void removeEnvironmentalData(EnvironmentalData data) {
        environmentalData.remove(data);
        data.setMine(null);
    }
}