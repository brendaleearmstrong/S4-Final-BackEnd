package com.misight.service;

import com.misight.model.Privileges;
import com.misight.repository.PrivilegesRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrivilegesService {
    private final PrivilegesRepo privilegesRepository;

    public PrivilegesService(PrivilegesRepo privilegesRepository) {
        this.privilegesRepository = privilegesRepository;
    }

    public List<Privileges> getAllPrivileges() {
        return privilegesRepository.findAll();
    }

    public Optional<Privileges> getPrivilegeById(Long id) {
        return privilegesRepository.findById(id);
    }

    public Optional<Privileges> getPrivilegeByName(String name) {
        return privilegesRepository.findByName(name);
    }

    public Privileges createPrivilege(String name) {
        if (privilegesRepository.existsByName(name)) {
            throw new IllegalArgumentException("Privilege name already exists");
        }

        Privileges privilege = new Privileges(name);
        return privilegesRepository.save(privilege);
    }

    public Optional<Privileges> updatePrivilege(Long id, String name) {
        return privilegesRepository.findById(id)
                .map(privilege -> {
                    if (!privilege.getName().equals(name) && privilegesRepository.existsByName(name)) {
                        throw new IllegalArgumentException("Privilege name already exists");
                    }

                    privilege.setName(name);
                    return privilegesRepository.save(privilege);
                });
    }

    public void deletePrivilege(Long id) {
        privilegesRepository.deleteById(id);
    }
}