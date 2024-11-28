package com.misight.controller;

import com.misight.model.Mines;
import com.misight.model.Minerals;
import com.misight.service.MinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mines")
@CrossOrigin(origins = "*")
public class MinesController {
    private final MinesService minesService;

    @Autowired
    public MinesController(MinesService minesService) {
        this.minesService = minesService;
    }

    @GetMapping
    public ResponseEntity<List<Mines>> getAllMines() {
        return ResponseEntity.ok(minesService.getAllMines());
    }

    @GetMapping("/withMinerals")
    public ResponseEntity<List<Mines>> getAllMinesWithMinerals() {
        return ResponseEntity.ok(minesService.getAllMinesWithMinerals());
    }

    @GetMapping("/mineral/{mineralId}")
    public ResponseEntity<List<Mines>> getMinesWithMineral(@PathVariable Long mineralId) {
        return ResponseEntity.ok(minesService.getMinesWithMineral(mineralId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mines> getMineById(@PathVariable Long id) {
        return minesService.getMineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/minerals")
    public ResponseEntity<List<Minerals>> getMineMinerals(@PathVariable Long id) {
        return minesService.getMineWithMinerals(id)
                .map(mine -> ResponseEntity.ok(mine.getMinerals().stream().toList()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mines> createMine(@RequestBody Mines mine) {
        return ResponseEntity.ok(minesService.createMine(mine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mines> updateMine(@PathVariable Long id, @RequestBody Mines mine) {
        return minesService.updateMine(id, mine)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{mineId}/minerals/{mineralId}")
    public ResponseEntity<String> addMineralToMine(
            @PathVariable Long mineId,
            @PathVariable Long mineralId) {
        try {
            boolean added = minesService.addMineralToMine(mineId, mineralId);
            if (added) {
                return ResponseEntity.ok("Mineral successfully added to mine");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding mineral to mine: " + e.getMessage());
        }
    }

    @DeleteMapping("/{mineId}/minerals/{mineralId}")
    public ResponseEntity<String> removeMineralFromMine(
            @PathVariable Long mineId,
            @PathVariable Long mineralId) {
        try {
            boolean removed = minesService.removeMineralFromMine(mineId, mineralId);
            if (removed) {
                return ResponseEntity.ok("Mineral successfully removed from mine");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing mineral from mine: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mines> partialUpdateMine(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return minesService.partialUpdateMine(id, updates)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMine(@PathVariable Long id) {
        if (minesService.deleteMine(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}