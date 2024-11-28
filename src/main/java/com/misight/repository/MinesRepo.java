package com.misight.repository;

import com.misight.model.Mines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MinesRepo extends JpaRepository<Mines, Long> {
    Optional<Mines> findByNameIgnoreCase(String name);
    List<Mines> findByCompanyIgnoreCase(String company);

    @Query("SELECT DISTINCT m FROM Mines m LEFT JOIN FETCH m.minerals WHERE m.id = :id")
    Optional<Mines> findByIdWithMinerals(@Param("id") Long id);

    @Query("SELECT DISTINCT m FROM Mines m")
    List<Mines> findAllBasic();

    @Query("SELECT DISTINCT m FROM Mines m LEFT JOIN FETCH m.minerals")
    List<Mines> findAllWithMinerals();

    @Query("SELECT DISTINCT m FROM Mines m JOIN m.minerals mineral WHERE mineral.id = :mineralId")
    List<Mines> findByMineralId(@Param("mineralId") Long mineralId);

    @Query("SELECT DISTINCT m FROM Mines m LEFT JOIN FETCH m.safetyData WHERE m.id = :id")
    Optional<Mines> findByIdWithSafetyData(@Param("id") Long id);

    @Query("SELECT DISTINCT m FROM Mines m LEFT JOIN FETCH m.environmentalData WHERE m.id = :id")
    Optional<Mines> findByIdWithEnvironmentalData(@Param("id") Long id);

    @Query("SELECT m FROM Mines m WHERE m.province.id = :provinceId")
    List<Mines> findByProvinceId(@Param("provinceId") Long provinceId);

    @Override
    @Query("SELECT DISTINCT m FROM Mines m LEFT JOIN FETCH m.province")
    List<Mines> findAll();
}