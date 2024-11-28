package com.misight.controller;

import com.misight.model.SafetyData;
import com.misight.service.SafetyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/safety-data")
@CrossOrigin(origins = "*")
public class SafetyDataController {
    private final SafetyDataService safetyDataService;

    @Autowired
    public SafetyDataController(SafetyDataService safetyDataService) {
        this.safetyDataService = safetyDataService;
    }

    @GetMapping
    public ResponseEntity<List<SafetyData>> getAllSafetyData() {
        return ResponseEntity.ok(safetyDataService.getAllSafetyData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SafetyData> getSafetyDataById(@PathVariable Long id) {
        return safetyDataService.getSafetyDataById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mine/{mineId}")
    public ResponseEntity<List<SafetyData>> getSafetyDataByMine(@PathVariable Long mineId) {
        return ResponseEntity.ok(safetyDataService.getSafetyDataByMine(mineId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<SafetyData>> getSafetyDataByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(safetyDataService.getSafetyDataByDateRange(startDate, endDate));
    }

    @GetMapping("/mine/{mineId}/date-range")
    public ResponseEntity<List<SafetyData>> getSafetyDataByMineAndDateRange(
            @PathVariable Long mineId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(safetyDataService.getSafetyDataByMineAndDateRange(mineId, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<SafetyData> createSafetyData(@RequestBody SafetyData safetyData) {
        return ResponseEntity.ok(safetyDataService.createSafetyData(safetyData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SafetyData> updateSafetyData(@PathVariable Long id, @RequestBody SafetyData safetyData) {
        return safetyDataService.updateSafetyData(id, safetyData)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSafetyData(@PathVariable Long id) {
        if (safetyDataService.deleteSafetyData(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
