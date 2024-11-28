package com.misight.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_privileges")
public class UserPrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "privilege_id")
    private int privilegeId;

    public UserPrivilege() {}

    public UserPrivilege(int userId, int privilegeId) {
        this.userId = userId;
        this.privilegeId = privilegeId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }
}