package com.misight.service;

import com.misight.model.SafetyData;
import com.misight.repository.SafetyDataRepo;
import com.misight.repository.MinesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SafetyDataService {
    private final SafetyDataRepo safetyDataRepo;
    private final MinesRepo minesRepo;

    @Autowired
    public SafetyDataService(SafetyDataRepo safetyDataRepo, MinesRepo minesRepo) {
        this.safetyDataRepo = safetyDataRepo;
        this.minesRepo = minesRepo;
    }

    public List<SafetyData> getAllSafetyData() {
        return safetyDataRepo.findAll();
    }

    public Optional<SafetyData> getSafetyDataById(Long id) {
        return safetyDataRepo.findById(id);
    }

    public List<SafetyData> getSafetyDataByMine(Long mineId) {
        return safetyDataRepo.findByMineId(mineId);
    }

    public List<SafetyData> getSafetyDataByDateRange(LocalDate startDate, LocalDate endDate) {
        return safetyDataRepo.findByDateRecordedBetween(startDate, endDate);
    }

    public List<SafetyData> getSafetyDataByMineAndDateRange(Long mineId, LocalDate startDate, LocalDate endDate) {
        return safetyDataRepo.findByMineIdAndDateRange(mineId, startDate, endDate);
    }

    public SafetyData createSafetyData(SafetyData safetyData) {
        return safetyDataRepo.save(safetyData);
    }

    public Optional<SafetyData> updateSafetyData(Long id, SafetyData safetyDataDetails) {
        return safetyDataRepo.findById(id)
                .map(existingData -> {
                    existingData.setDateRecorded(safetyDataDetails.getDateRecorded());
                    existingData.setLostTimeIncidents(safetyDataDetails.getLostTimeIncidents());
                    existingData.setNearMisses(safetyDataDetails.getNearMisses());
                    existingData.setSafetyLevel(safetyDataDetails.getSafetyLevel());
                    return safetyDataRepo.save(existingData);
                });
    }

    public boolean deleteSafetyData(Long id) {
        return safetyDataRepo.findById(id)
                .map(data -> {
                    safetyDataRepo.delete(data);
                    return true;
                })
                .orElse(false);
    }
}
