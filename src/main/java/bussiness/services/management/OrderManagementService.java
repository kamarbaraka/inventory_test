package bussiness.services.management;

import persistence.*;

/**
 *  a class to simulate a service to manage orders.
 *  @author kamar baraka.*/

public class OrderManagementService {

    public boolean addOrder(Order order, Bill bill, Customer customer, Payment payment, Address address){

        return true;
    }

    public boolean dispatchPendingOrders(){

        return true;
    }

    public boolean dispatchOrder(Order order){

        return true;
    }
}
