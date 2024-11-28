package com.misight.repository;

import com.misight.model.Pollutants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PollutantsRepo extends JpaRepository<Pollutants, Long> {
    List<Pollutants> findByCategory(String category);
    Optional<Pollutants> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT p FROM Pollutants p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Pollutants> findByNameContaining(@Param("name") String name);
}
