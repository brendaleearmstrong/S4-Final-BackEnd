package com.misight.repository;

import com.misight.model.Minerals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MineralsRepo extends JpaRepository<Minerals, Long> {
    @Query("SELECT m FROM Minerals m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Minerals> findByNameContaining(@Param("name") String name);

    boolean existsByName(String name);

    @Query("SELECT m FROM Minerals m JOIN m.mines mine WHERE mine.id = :mineId")
    List<Minerals> findByMineId(@Param("mineId") Long mineId);
}
