package com.misight.service;

import com.misight.model.Mines;
import com.misight.model.Minerals;
import com.misight.repository.MinesRepo;
import com.misight.repository.MineralsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;

@Service
@Transactional
public class MinesService {
    private final MinesRepo minesRepo;
    private final MineralsRepo mineralsRepo;

    @Autowired
    public MinesService(MinesRepo minesRepo, MineralsRepo mineralsRepo) {
        this.minesRepo = minesRepo;
        this.mineralsRepo = mineralsRepo;
    }

    public List<Mines> getAllMines() {
        return minesRepo.findAll();
    }

    public List<Mines> getAllMinesWithMinerals() {
        return minesRepo.findAllWithMinerals();
    }

    public List<Mines> getMinesWithMineral(Long mineralId) {
        return minesRepo.findByMineralId(mineralId);
    }

    public Optional<Mines> getMineById(Long id) {
        return minesRepo.findById(id);
    }

    public Optional<Mines> getMineWithMinerals(Long id) {
        return minesRepo.findByIdWithMinerals(id);
    }

    @Transactional
    public Mines createMine(Mines mine) {
        if (mine.getMinerals() == null) {
            mine.setMinerals(new HashSet<>());
        }
        return minesRepo.save(mine);
    }

    @Transactional
    public Optional<Mines> updateMine(Long id, Mines mineDetails) {
        return minesRepo.findById(id)
                .map(existingMine -> {
                    existingMine.setName(mineDetails.getName());
                    existingMine.setCompany(mineDetails.getCompany());
                    existingMine.setLocation(mineDetails.getLocation());
                    existingMine.setProvince(mineDetails.getProvince());
                    return minesRepo.save(existingMine);
                });
    }

    @Transactional
    public boolean addMineralToMine(Long mineId, Long mineralId) {
        try {
            Optional<Mines> mineOpt = minesRepo.findByIdWithMinerals(mineId);
            Optional<Minerals> mineralOpt = mineralsRepo.findById(mineralId);

            if (mineOpt.isEmpty() || mineralOpt.isEmpty()) {
                return false;
            }

            Mines mine = mineOpt.get();
            Minerals mineral = mineralOpt.get();

            if (mine.getMinerals() == null) {
                mine.setMinerals(new HashSet<>());
            }

            if (!mine.getMinerals().contains(mineral)) {
                mine.addMineral(mineral);
                minesRepo.save(mine);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean removeMineralFromMine(Long mineId, Long mineralId) {
        try {
            Optional<Mines> mineOpt = minesRepo.findByIdWithMinerals(mineId);
            Optional<Minerals> mineralOpt = mineralsRepo.findById(mineralId);

            if (mineOpt.isEmpty() || mineralOpt.isEmpty()) {
                return false;
            }

            Mines mine = mineOpt.get();
            Minerals mineral = mineralOpt.get();

            if (mine.getMinerals() != null && mine.getMinerals().contains(mineral)) {
                mine.removeMineral(mineral);
                minesRepo.save(mine);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Mines> partialUpdateMine(Long id, Map<String, Object> updates) {
        return minesRepo.findById(id)
                .map(existingMine -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "name" -> existingMine.setName((String) value);
                            case "company" -> existingMine.setCompany((String) value);
                            case "location" -> existingMine.setLocation((String) value);
                        }
                    });
                    return minesRepo.save(existingMine);
                });
    }

    @Transactional
    public boolean deleteMine(Long id) {
        return minesRepo.findById(id)
                .map(mine -> {
                    minesRepo.delete(mine);
                    return true;
                })
                .orElse(false);
    }

    public List<Mines> findByCompanyName(String company) {
        return minesRepo.findByCompanyIgnoreCase(company);
    }

    public Optional<Mines> findByName(String name) {
        return minesRepo.findByNameIgnoreCase(name);
    }

    public List<Mines> findByProvinceId(Long provinceId) {
        return minesRepo.findByProvinceId(provinceId);
    }

    public Optional<Mines> getMineWithSafetyData(Long id) {
        return minesRepo.findByIdWithSafetyData(id);
    }

    public Optional<Mines> getMineWithEnvironmentalData(Long id) {
        return minesRepo.findByIdWithEnvironmentalData(id);
    }
}