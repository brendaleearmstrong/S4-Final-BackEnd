package com.misight.repository;

import com.misight.model.SafetyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SafetyDataRepo extends JpaRepository<SafetyData, Long> {
    List<SafetyData> findByMineId(Long mineId);

    List<SafetyData> findByDateRecordedBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM SafetyData s WHERE s.mine.id = :mineId AND s.dateRecorded BETWEEN :startDate AND :endDate")
    List<SafetyData> findByMineIdAndDateRange(
            @Param("mineId") Long mineId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
