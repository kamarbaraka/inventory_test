/*
 * Copyright (c) 2023. This code is protected under the GPL 2.0 license.
 */

package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * an enumeration to store user role constants.
 * @author kamar baraka*/

@Entity
public class Role {

    @Id
    @GeneratedValue
    private long id;
    private String role;
    private String description;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

