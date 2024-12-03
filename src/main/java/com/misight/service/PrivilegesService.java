package com.misight.service;

import com.misight.model.Privileges;
import com.misight.repository.PrivilegesRepo;
import com.misight.model.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrivilegesService {

    private final PrivilegesRepo privilegesRepo;

    @Autowired
    public PrivilegesService(PrivilegesRepo privilegesRepo) {
        this.privilegesRepo = privilegesRepo;
    }

    public List<Privileges> createPrivileges(List<Privileges> privileges) {
        return privilegesRepo.saveAll(privileges);
    }

    public Privileges createPrivilege(Privileges privilege) {
        return privilegesRepo.save(privilege);
    }

    public Privileges getPrivilegeById(Long id) {
        return privilegesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with ID: " + id));
    }

    public List<Privileges> getAllPrivileges() {
        return privilegesRepo.findAll();
    }

    public Privileges updatePrivilege(Long id, Privileges privilegeDetails) {
        Privileges privilege = getPrivilegeById(id);
        privilege.setName(privilegeDetails.getName());
        return privilegesRepo.save(privilege);
    }

    public void deletePrivilege(Long id) {
        if (!privilegesRepo.existsById(id)) {
            throw new ResourceNotFoundException("Privilege not found with ID: " + id);
        }
        privilegesRepo.deleteById(id);
    }
}