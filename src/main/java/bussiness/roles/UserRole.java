package bussiness.roles;

import persistence.Role;

/**
 *  a class to describe a normal user.
 *  @author kamar baraka*/

public class UserRole {

    private final Role instance;

    private UserRole(){

        this.instance = new Role();
        this.instance.setRole("USER");
        this.instance.setDescription("a normal user with basic read access");
    }

    public static Role getInstance(){
        UserRole role = new UserRole();
        return role.instance;
    }
}
