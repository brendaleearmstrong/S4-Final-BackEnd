// MineralsService.java
package com.misight.service;

import com.misight.model.Minerals;
import com.misight.repository.MineralsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MineralsService {
    private final MineralsRepo mineralsRepo;

    @Autowired
    public MineralsService(MineralsRepo mineralsRepo) {
        this.mineralsRepo = mineralsRepo;
    }

    public List<Minerals> getAllMinerals() {
        return mineralsRepo.findAll();
    }

    public Optional<Minerals> getMineralById(Long id) {
        return mineralsRepo.findById(id);
    }

    public List<Minerals> getMineralsByMine(Long mineId) {
        return mineralsRepo.findByMineId(mineId);
    }

    public Minerals createMineral(Minerals mineral) {
        if (mineralsRepo.existsByName(mineral.getName())) {
            throw new IllegalArgumentException("Mineral name already exists");
        }
        return mineralsRepo.save(mineral);
    }

    public Optional<Minerals> updateMineral(Long id, Minerals mineralDetails) {
        return mineralsRepo.findById(id)
                .map(existingMineral -> {
                    existingMineral.setName(mineralDetails.getName());
                    return mineralsRepo.save(existingMineral);
                });
    }

    public boolean deleteMineral(Long id) {
        return mineralsRepo.findById(id)
                .map(mineral -> {
                    mineralsRepo.delete(mineral);
                    return true;
                })
                .orElse(false);
    }

    public List<Minerals> searchMinerals(String name) {
        return mineralsRepo.findByNameContaining(name);
    }
}
