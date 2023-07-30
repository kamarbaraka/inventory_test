package bussiness.roles;

import persistence.Role;

/**
 *  a class to define an accountant.
 *  @author kamar baraka.*/

public class AccountantRole {

    private final Role instance;
    private AccountantRole(){
        this.instance = new Role();
        this.instance.setRole("Accountant");
        this.instance.setDescription("has access to accounting operations");
    }

    public static Role getInstance(){
        AccountantRole role = new AccountantRole();
        return role.instance;
    }
}
