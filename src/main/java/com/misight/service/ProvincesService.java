package com.misight.service;

import com.misight.model.Provinces;
import com.misight.repository.ProvincesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProvincesService {
    private final ProvincesRepo provincesRepo;

    @Autowired
    public ProvincesService(ProvincesRepo provincesRepo) {
        this.provincesRepo = provincesRepo;
    }

    public List<Provinces> getAllProvinces() {
        return provincesRepo.findAllBasic();
    }

    public Optional<Provinces> getProvinceById(Long id) {
        return provincesRepo.findById(id);
    }

    public Optional<Provinces> getProvinceWithMines(Long id) {
        return provincesRepo.findByIdWithMines(id);
    }

    public Optional<Provinces> getProvinceWithStations(Long id) {
        return provincesRepo.findByIdWithStations(id);
    }

    public Provinces createProvince(Provinces province) {
        if (provincesRepo.existsByNameIgnoreCase(province.getName())) {
            throw new IllegalArgumentException("Province with name " + province.getName() + " already exists");
        }
        return provincesRepo.save(province);
    }

    public Optional<Provinces> updateProvince(Long id, Provinces provinceDetails) {
        return provincesRepo.findById(id)
                .map(existingProvince -> {
                    if (!existingProvince.getName().equals(provinceDetails.getName()) &&
                            provincesRepo.existsByNameIgnoreCase(provinceDetails.getName())) {
                        throw new IllegalArgumentException("Province name already exists");
                    }
                    existingProvince.setName(provinceDetails.getName());
                    return provincesRepo.save(existingProvince);
                });
    }

    public boolean deleteProvince(Long id) {
        return provincesRepo.findById(id)
                .map(province -> {
                    provincesRepo.delete(province);
                    return true;
                })
                .orElse(false);
    }
}
