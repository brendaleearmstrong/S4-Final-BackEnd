package com.misight.repository;

import com.misight.model.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegesRepo extends JpaRepository<Privileges, Long> {
}
