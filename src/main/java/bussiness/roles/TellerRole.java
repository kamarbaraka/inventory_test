package bussiness.roles;

import persistence.Role;

/**
 *  a class to describe a teller.
 *  @author kamar baraka.*/

public class TellerRole {

    private final Role instance;

    private TellerRole(){

        this.instance = new Role();
        this.instance.setRole("TELLER");
        this.instance.setDescription("has access to teller operations");
    }

    public static Role getInstance(){
        TellerRole role = new TellerRole();
        return role.instance;
    }
}
