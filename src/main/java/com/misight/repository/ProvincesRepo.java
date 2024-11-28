package com.misight.repository;

import com.misight.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProvincesRepo extends JpaRepository<Provinces, Long> {
    @Query("SELECT p FROM Provinces p")
    List<Provinces> findAllBasic();

    @Query("SELECT DISTINCT p FROM Provinces p LEFT JOIN FETCH p.mines WHERE p.id = :id")
    Optional<Provinces> findByIdWithMines(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Provinces p LEFT JOIN FETCH p.monitoringStations WHERE p.id = :id")
    Optional<Provinces> findByIdWithStations(@Param("id") Long id);

    Optional<Provinces> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
