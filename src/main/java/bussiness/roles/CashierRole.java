package bussiness.roles;

import persistence.Role;

/**
 *  a class to describe a cashier.
 *  @author kamar baraka.*/

public class CashierRole {

    private final Role instance;

    private CashierRole(){

        this.instance = new Role();
        this.instance.setRole("CASHIER");
        this.instance.setDescription("has access to payment operations");
    }

    public static Role getInstance(){
        CashierRole role = new CashierRole();
        return role.instance;
    }
}
