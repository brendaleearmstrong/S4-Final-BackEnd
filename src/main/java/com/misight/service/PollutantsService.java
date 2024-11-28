package com.misight.service;

import com.misight.model.Pollutants;
import com.misight.repository.PollutantsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PollutantsService {
    private final PollutantsRepo pollutantsRepo;

    @Autowired
    public PollutantsService(PollutantsRepo pollutantsRepo) {
        this.pollutantsRepo = pollutantsRepo;
    }

    public List<Pollutants> getAllPollutants() {
        return pollutantsRepo.findAll();
    }

    public Optional<Pollutants> getPollutantById(Long id) {
        return pollutantsRepo.findById(id);
    }

    public List<Pollutants> getPollutantsByCategory(String category) {
        return pollutantsRepo.findByCategory(category);
    }

    public Pollutants createPollutant(Pollutants pollutant) {
        if (pollutantsRepo.existsByName(pollutant.getName())) {
            throw new IllegalArgumentException("Pollutant already exists");
        }
        return pollutantsRepo.save(pollutant);
    }

    public Optional<Pollutants> updatePollutant(Long id, Pollutants pollutantDetails) {
        return pollutantsRepo.findById(id)
                .map(existingPollutant -> {
                    if (!existingPollutant.getName().equals(pollutantDetails.getName()) &&
                            pollutantsRepo.existsByName(pollutantDetails.getName())) {
                        throw new IllegalArgumentException("Pollutant name already exists");
                    }
                    existingPollutant.setName(pollutantDetails.getName());
                    existingPollutant.setCategory(pollutantDetails.getCategory());
                    existingPollutant.setUnit(pollutantDetails.getUnit());
                    existingPollutant.setBenchmarkValue(pollutantDetails.getBenchmarkValue());
                    existingPollutant.setBenchmarkType(pollutantDetails.getBenchmarkType());
                    return pollutantsRepo.save(existingPollutant);
                });
    }

    public boolean deletePollutant(Long id) {
        return pollutantsRepo.findById(id)
                .map(pollutant -> {
                    pollutantsRepo.delete(pollutant);
                    return true;
                })
                .orElse(false);
    }

    public List<Pollutants> searchPollutants(String name) {
        return pollutantsRepo.findByNameContaining(name);
    }
}