package com.misight.model;

import com.misight.model.Mines;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SafetyData {

    public enum SafetyLevel {
        CRITICAL,
        FAIR,
        GOOD,
        EXCELLENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateRecorded;
    private int lostTimeIncidents;
    private int nearMisses;

    @Enumerated(EnumType.STRING)
    private SafetyLevel safetyLevel;

    @ManyToOne
    @JoinColumn(name = "mine_id")
    private Mines mine;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(LocalDate dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public int getLostTimeIncidents() {
        return lostTimeIncidents;
    }

    public void setLostTimeIncidents(int lostTimeIncidents) {
        this.lostTimeIncidents = lostTimeIncidents;
    }

    public int getNearMisses() {
        return nearMisses;
    }

    public void setNearMisses(int nearMisses) {
        this.nearMisses = nearMisses;
    }

    public SafetyLevel getSafetyLevel() {
        return safetyLevel;
    }

    public void setSafetyLevel(SafetyLevel safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

    public Mines getMine() {
        return mine;
    }

    public void setMine(Mines mine) {
        this.mine = mine;
    }
}
