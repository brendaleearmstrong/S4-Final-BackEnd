package com.misight.controller;

import com.misight.model.Provinces;
import com.misight.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinces")
@CrossOrigin(origins = "*")
public class ProvincesController {
    private final ProvincesService provincesService;

    @Autowired
    public ProvincesController(ProvincesService provincesService) {
        this.provincesService = provincesService;
    }

    @GetMapping
    public ResponseEntity<List<Provinces>> getAllProvinces() {
        return ResponseEntity.ok(provincesService.getAllProvinces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provinces> getProvinceById(@PathVariable Long id) {
        return provincesService.getProvinceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/mines")
    public ResponseEntity<Provinces> getProvinceWithMines(@PathVariable Long id) {
        return provincesService.getProvinceWithMines(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/stations")
    public ResponseEntity<Provinces> getProvinceWithStations(@PathVariable Long id) {
        return provincesService.getProvinceWithStations(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProvince(@RequestBody Provinces province) {
        try {
            return ResponseEntity.ok(provincesService.createProvince(province));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable Long id, @RequestBody Provinces province) {
        try {
            return provincesService.updateProvince(id, province)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        if (provincesService.deleteProvince(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}