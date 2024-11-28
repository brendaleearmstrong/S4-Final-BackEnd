package com.misight.controller;

import com.misight.model.UserPrivilege;
import com.misight.service.UserPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-privileges")
public class UserPrivilegeController {

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @PostMapping("/bulk")
    public ResponseEntity<List<UserPrivilege>> assignPrivileges(@RequestBody List<UserPrivilege> userPrivileges) {
        try {
            List<UserPrivilege> assigned = userPrivilegeService.assignPrivileges(userPrivileges);
            return new ResponseEntity<>(assigned, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<UserPrivilege> assignPrivilege(@RequestBody UserPrivilege userPrivilege) {
        try {
            UserPrivilege assigned = userPrivilegeService.assignPrivilege(userPrivilege);
            return new ResponseEntity<>(assigned, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{userId}/{privilegeId}")
    public ResponseEntity<Void> removePrivilege(
            @PathVariable int userId,
            @PathVariable int privilegeId) {
        try {
            userPrivilegeService.removePrivilege(userId, privilegeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPrivileges(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(
                    userPrivilegeService.getUserPrivileges(userId),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}