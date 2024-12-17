package com.misight.controller;

import com.misight.model.Privileges;
import com.misight.service.PrivilegesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/privileges")
@CrossOrigin(origins = "*")
public class PrivilegesController {
    private final PrivilegesService privilegesService;

    public PrivilegesController(PrivilegesService privilegesService) {
        this.privilegesService = privilegesService;
    }

    @GetMapping
    public ResponseEntity<List<Privileges>> getAllPrivileges() {
        return ResponseEntity.ok(privilegesService.getAllPrivileges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privileges> getPrivilegeById(@PathVariable Long id) {
        return privilegesService.getPrivilegeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Privileges> getPrivilegeByName(@PathVariable String name) {
        return privilegesService.getPrivilegeByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Privileges> createPrivilege(@RequestParam String name) {
        try {
            Privileges privilege = privilegesService.createPrivilege(name);
            return ResponseEntity.ok(privilege);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Privileges> updatePrivilege(
            @PathVariable Long id,
            @RequestParam String name) {
        try {
            return privilegesService.updatePrivilege(id, name)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        privilegesService.deletePrivilege(id);
        return ResponseEntity.ok().build();
    }
}