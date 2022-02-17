package com.emyasa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserAccountRole {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, unique = true)
    private String role;

    public String getRole() {
        return role;
    }

    public UserAccountRole(String role) {
        Validate.notEmpty(role, "role must not be empty");

        this.role = role;
    }

    protected UserAccountRole() {}
}
