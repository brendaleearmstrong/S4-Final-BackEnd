package com.misight.service;

import com.misight.model.EnvironmentalData;
import com.misight.repository.EnvironmentalDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnvironmentalDataService {
    private final EnvironmentalDataRepo environmentalDataRepo;

    @Autowired
    public EnvironmentalDataService(EnvironmentalDataRepo environmentalDataRepo) {
        this.environmentalDataRepo = environmentalDataRepo;
    }

    public List<EnvironmentalData> getAllEnvironmentalData() {
        return environmentalDataRepo.findAll();
    }

    public Optional<EnvironmentalData> getEnvironmentalDataById(Long id) {
        return environmentalDataRepo.findById(id);
    }

    public List<EnvironmentalData> getEnvironmentalDataByMine(Long mineId) {
        return environmentalDataRepo.findByMineId(mineId);
    }

    public List<EnvironmentalData> getEnvironmentalDataByStation(Long stationId) {
        return environmentalDataRepo.findByMonitoringStationId(stationId);
    }

    public List<EnvironmentalData> getEnvironmentalDataByDateRange(LocalDateTime start, LocalDateTime end) {
        return environmentalDataRepo.findByMeasurementDateBetween(start, end);
    }

    public List<EnvironmentalData> getEnvironmentalDataByMineAndDateRange(Long mineId, LocalDateTime start, LocalDateTime end) {
        return environmentalDataRepo.findByMineIdAndDateRange(mineId, start, end);
    }

    public EnvironmentalData createEnvironmentalData(EnvironmentalData environmentalData) {
        return environmentalDataRepo.save(environmentalData);
    }

    public Optional<EnvironmentalData> updateEnvironmentalData(Long id, EnvironmentalData dataDetails) {
        return environmentalDataRepo.findById(id)
                .map(existingData -> {
                    existingData.setMeasuredValue(dataDetails.getMeasuredValue());
                    existingData.setMeasurementDate(dataDetails.getMeasurementDate());
                    existingData.setNotes(dataDetails.getNotes());
                    existingData.setPollutant(dataDetails.getPollutant());
                    existingData.setMonitoringStation(dataDetails.getMonitoringStation());
                    return environmentalDataRepo.save(existingData);
                });
    }

    public boolean deleteEnvironmentalData(Long id) {
        return environmentalDataRepo.findById(id)
                .map(data -> {
                    environmentalDataRepo.delete(data);
                    return true;
                })
                .orElse(false);
    }
}