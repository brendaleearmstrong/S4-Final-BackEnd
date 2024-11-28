package com.misight.service;

import com.misight.model.MonitoringStations;
import com.misight.repository.MonitoringStationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MonitoringStationsService {

    private final MonitoringStationsRepo monitoringStationsRepo;

    @Autowired
    public MonitoringStationsService(MonitoringStationsRepo monitoringStationsRepo) {
        this.monitoringStationsRepo = monitoringStationsRepo;
    }

    public List<MonitoringStations> getAllStations() {
        return monitoringStationsRepo.findAll();
    }

    public Optional<MonitoringStations> getStationById(Long id) {
        return monitoringStationsRepo.findById(id);
    }

    public MonitoringStations createStation(MonitoringStations station) {
        return monitoringStationsRepo.save(station);
    }

    public Optional<MonitoringStations> updateStation(Long id, MonitoringStations stationDetails) {
        Optional<MonitoringStations> existingStation = monitoringStationsRepo.findById(id);

        if (existingStation.isPresent()) {
            MonitoringStations station = existingStation.get();
            station.setLocation(stationDetails.getLocation());
            station.setProvince(stationDetails.getProvince());
            station.setPollutants(stationDetails.getPollutants());
            return Optional.of(monitoringStationsRepo.save(station));
        }
        return Optional.empty();
    }

    public boolean deleteStation(Long id) {
        if (monitoringStationsRepo.existsById(id)) {
            monitoringStationsRepo.deleteById(id);
            return true;
        }
        return false;
    }
}