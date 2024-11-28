package com.misight.repository;

import com.misight.model.MonitoringStations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MonitoringStationsRepo extends JpaRepository<MonitoringStations, Long> {
    List<MonitoringStations> findByLocation(String location);

    @Query("SELECT ms FROM MonitoringStations ms WHERE ms.province.id = :provinceId")
    List<MonitoringStations> findByProvinceId(@Param("provinceId") Long provinceId);
}