package bussiness.roles;

import persistence.Role;

/**
 *  a class to define an admin role.
 *  @author kamar baraka.*/

public class AdminRole {

    private final Role instance;

    private AdminRole() {

        this.instance = new Role();
        this.instance.setRole("ADMIN");
        this.instance.setDescription("has access to all the views");
    }

    public static Role getInstance(){
        AdminRole role = new AdminRole();
        return role.instance;
    }
}
