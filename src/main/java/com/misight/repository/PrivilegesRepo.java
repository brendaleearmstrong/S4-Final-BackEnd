package com.misight.repository;

import com.misight.model.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PrivilegesRepo extends JpaRepository<Privileges, Long> {
    Optional<Privileges> findByName(String name);
    Set<Privileges> findByNameIn(Set<String> names);
    boolean existsByName(String name);
}