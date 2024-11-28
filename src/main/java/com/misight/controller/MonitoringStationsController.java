package com.misight.controller;

import com.misight.model.MonitoringStations;
import com.misight.service.MonitoringStationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/monitoringstations")
@CrossOrigin(origins = "*")
public class MonitoringStationsController {

    private final MonitoringStationsService service;

    @Autowired
    public MonitoringStationsController(MonitoringStationsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MonitoringStations>> getAllStations() {
        try {
            List<MonitoringStations> stations = service.getAllStations();
            if (stations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(stations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitoringStations> getStationById(@PathVariable Long id) {
        try {
            return service.getStationById(id)
                    .map(station -> new ResponseEntity<>(station, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<MonitoringStations> createStation(@RequestBody MonitoringStations station) {
        try {
            MonitoringStations newStation = service.createStation(station);
            return new ResponseEntity<>(newStation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitoringStations> updateStation(@PathVariable Long id, @RequestBody MonitoringStations station) {
        try {
            return service.updateStation(id, station)
                    .map(updatedStation -> new ResponseEntity<>(updatedStation, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStation(@PathVariable Long id) {
        try {
            boolean deleted = service.deleteStation(id);
            return deleted ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}