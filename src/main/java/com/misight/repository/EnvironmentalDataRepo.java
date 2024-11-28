package com.misight.repository;

import com.misight.model.EnvironmentalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnvironmentalDataRepo extends JpaRepository<EnvironmentalData, Long> {
    List<EnvironmentalData> findByMineId(Long mineId);

    List<EnvironmentalData> findByMonitoringStationId(Long stationId);

    List<EnvironmentalData> findByPollutantId(Long pollutantId);

    List<EnvironmentalData> findByMeasurementDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT e FROM EnvironmentalData e WHERE e.mine.id = :mineId AND e.measurementDate BETWEEN :start AND :end")
    List<EnvironmentalData> findByMineIdAndDateRange(
            @Param("mineId") Long mineId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}