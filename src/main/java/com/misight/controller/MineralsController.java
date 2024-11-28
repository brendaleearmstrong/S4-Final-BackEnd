package com.misight.controller;

import com.misight.model.Minerals;
import com.misight.service.MineralsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/minerals")
@CrossOrigin(origins = "*")
public class MineralsController {
    private final MineralsService mineralsService;

    @Autowired
    public MineralsController(MineralsService mineralsService) {
        this.mineralsService = mineralsService;
    }

    @GetMapping
    public ResponseEntity<List<Minerals>> getAllMinerals() {
        return ResponseEntity.ok(mineralsService.getAllMinerals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Minerals> getMineralById(@PathVariable Long id) {
        return mineralsService.getMineralById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mine/{mineId}")
    public ResponseEntity<List<Minerals>> getMineralsByMine(@PathVariable Long mineId) {
        return ResponseEntity.ok(mineralsService.getMineralsByMine(mineId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Minerals>> searchMinerals(@RequestParam String name) {
        return ResponseEntity.ok(mineralsService.searchMinerals(name));
    }

    @PostMapping
    public ResponseEntity<?> createMineral(@RequestBody Minerals mineral) {
        try {
            return ResponseEntity.ok(mineralsService.createMineral(mineral));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMineral(@PathVariable Long id, @RequestBody Minerals mineral) {
        return mineralsService.updateMineral(id, mineral)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMineral(@PathVariable Long id) {
        if (mineralsService.deleteMineral(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
