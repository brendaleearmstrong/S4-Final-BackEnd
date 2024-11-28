package com.misight.controller;

import com.misight.model.EnvironmentalData;
import com.misight.service.EnvironmentalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/environmental-data")
@CrossOrigin(origins = "*")
public class EnvironmentalDataController {
    private final EnvironmentalDataService environmentalDataService;

    @Autowired
    public EnvironmentalDataController(EnvironmentalDataService environmentalDataService) {
        this.environmentalDataService = environmentalDataService;
    }

    @GetMapping
    public ResponseEntity<List<EnvironmentalData>> getAllEnvironmentalData() {
        return ResponseEntity.ok(environmentalDataService.getAllEnvironmentalData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentalData> getEnvironmentalDataById(@PathVariable Long id) {
        return environmentalDataService.getEnvironmentalDataById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mine/{mineId}")
    public ResponseEntity<List<EnvironmentalData>> getEnvironmentalDataByMine(@PathVariable Long mineId) {
        return ResponseEntity.ok(environmentalDataService.getEnvironmentalDataByMine(mineId));
    }

    @GetMapping("/station/{stationId}")
    public ResponseEntity<List<EnvironmentalData>> getEnvironmentalDataByStation(@PathVariable Long stationId) {
        return ResponseEntity.ok(environmentalDataService.getEnvironmentalDataByStation(stationId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<EnvironmentalData>> getEnvironmentalDataByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(environmentalDataService.getEnvironmentalDataByDateRange(start, end));
    }

    @GetMapping("/mine/{mineId}/date-range")
    public ResponseEntity<List<EnvironmentalData>> getEnvironmentalDataByMineAndDateRange(
            @PathVariable Long mineId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(environmentalDataService.getEnvironmentalDataByMineAndDateRange(mineId, start, end));
    }

    @PostMapping
    public ResponseEntity<EnvironmentalData> createEnvironmentalData(@RequestBody EnvironmentalData environmentalData) {
        return ResponseEntity.ok(environmentalDataService.createEnvironmentalData(environmentalData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentalData> updateEnvironmentalData(
            @PathVariable Long id,
            @RequestBody EnvironmentalData environmentalData) {
        return environmentalDataService.updateEnvironmentalData(id, environmentalData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironmentalData(@PathVariable Long id) {
        if (environmentalDataService.deleteEnvironmentalData(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}