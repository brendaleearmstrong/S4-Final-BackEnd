package com.misight.controller;

import com.misight.model.Privileges;
import com.misight.service.PrivilegesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privileges")
public class PrivilegesController {

    private final PrivilegesService privilegesService;

    @Autowired
    public PrivilegesController(PrivilegesService privilegesService) {
        this.privilegesService = privilegesService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Privileges>> createPrivileges(@RequestBody List<Privileges> privileges) {
        List<Privileges> createdPrivileges = privilegesService.createPrivileges(privileges);
        return new ResponseEntity<>(createdPrivileges, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Privileges> createPrivilege(@RequestBody Privileges privilege) {
        Privileges createdPrivilege = privilegesService.createPrivilege(privilege);
        return new ResponseEntity<>(createdPrivilege, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privileges> getPrivilegeById(@PathVariable Long id) {
        Privileges privilege = privilegesService.getPrivilegeById(id);
        return new ResponseEntity<>(privilege, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Privileges>> getAllPrivileges() {
        List<Privileges> privileges = privilegesService.getAllPrivileges();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Privileges> updatePrivilege(@PathVariable Long id, @RequestBody Privileges privilegeDetails) {
        Privileges updatedPrivilege = privilegesService.updatePrivilege(id, privilegeDetails);
        return new ResponseEntity<>(updatedPrivilege, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        privilegesService.deletePrivilege(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}