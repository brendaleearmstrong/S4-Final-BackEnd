package com.misight.controller;

import com.misight.model.Pollutants;
import com.misight.service.PollutantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pollutants")
@CrossOrigin(origins = "*")
public class PollutantsController {
    private final PollutantsService pollutantsService;

    @Autowired
    public PollutantsController(PollutantsService pollutantsService) {
        this.pollutantsService = pollutantsService;
    }

    @GetMapping
    public ResponseEntity<List<Pollutants>> getAllPollutants() {
        return ResponseEntity.ok(pollutantsService.getAllPollutants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pollutants> getPollutantById(@PathVariable Long id) {
        return pollutantsService.getPollutantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Pollutants>> getPollutantsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(pollutantsService.getPollutantsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pollutants>> searchPollutants(@RequestParam String name) {
        return ResponseEntity.ok(pollutantsService.searchPollutants(name));
    }

    @PostMapping
    public ResponseEntity<?> createPollutant(@RequestBody Pollutants pollutant) {
        try {
            return ResponseEntity.ok(pollutantsService.createPollutant(pollutant));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePollutant(@PathVariable Long id, @RequestBody Pollutants pollutant) {
        try {
            return pollutantsService.updatePollutant(id, pollutant)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePollutant(@PathVariable Long id) {
        if (pollutantsService.deletePollutant(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
