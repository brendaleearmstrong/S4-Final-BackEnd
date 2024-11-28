package com.misight.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RootController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo.put("name", "Misight API");
        apiInfo.put("version", "1.0.0");
        apiInfo.put("description", "Mining Data Management System API");
        apiInfo.put("endpoints", new String[] {
                "/mines",
                "/minerals",
                "/provinces",
                "/environmental-data",
                "/monitoring-stations",
                "/safety-data",
                "/users",
                "/auth"
        });

        return ResponseEntity.ok(apiInfo);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }
}