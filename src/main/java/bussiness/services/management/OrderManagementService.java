/*
 * Copyright (c) 2023. This code is protected under the GPL 2.0 license.
 */

package bussiness.services.management;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import persistence.*;

import java.util.List;

/**
 *  a class to simulate a service to manage orders.
 *  @author kamar baraka.*/

public class OrderManagementService {

    private final String PERSISTENCE_UNIT = "inventory_management";

    /*declare pending orders list*/
    private List<ItemOrder> pendingOrders;
    /*declare dispatched orders list*/
    private List<ItemOrder> dispatchedOrders;
    /*declare completed orders lists*/
    private List<ItemOrder> completedOrders;

    public boolean addOrder(Address address, Payment payment, Customer customer, Bill bill, ItemOrder itemOrder){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                PERSISTENCE_UNIT
        )) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*persist the order objects*/
                manager.persist(address);
                manager.persist(payment);
                manager.persist(customer);
                manager.persist(bill);
                manager.persist(itemOrder);

                /*commit the transaction*/
                transaction.commit();

                return true;

            }
        }
    }

    public boolean dispatchPendingOrders(){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                PERSISTENCE_UNIT
        )) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                try
                {
                    String pendingStatus = "pending";

                    /*dispatch pending orders*/
                    List<ItemOrder> itemOrders = manager.createQuery(
                            "select persistedOrder from ItemOrder persistedOrder where status=:pendingStatus",
                            ItemOrder.class
                    ).setParameter("pendingStatus", pendingStatus).getResultList();

                    itemOrders.forEach(itemOrder -> itemOrder.setStatus("dispatched"));

                    /*commit transaction*/
                    transaction.commit();

                    return true;
                }
                catch (Exception exception){

                    /*return false if fail*/
                    return false;
                }

            }

        }
    }

    public boolean dispatchOrder(long orderId){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                PERSISTENCE_UNIT
        )) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*dispatch the order*/
                try
                {
                    ItemOrder itemOrder = manager.createQuery(
                            "select persistedOrder from ItemOrder persistedOrder where id=:orderId",
                            ItemOrder.class
                    ).setParameter("orderId", orderId).getSingleResult();

                    itemOrder.setStatus("dispatched");

                    /*commit transaction*/
                    transaction.commit();

                    return true;

                }
                catch (Exception exception){

                    /*return false on fail*/
                    System.out.println("no such order");
                    return false;
                }

            }

        }
    }

    public boolean updateCount(ItemInventory inventory, int numberOfOrderedItems){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*create a query for the inventory*/
                ItemInventory persistedInventory = manager.createQuery(
                        "select itemInventory from ItemInventory itemInventory where inventoryName=:name",
                        ItemInventory.class
                ).setParameter("name", inventory.getInventoryName()).getSingleResult();

                /*update the count*/
                persistedInventory.setItemCount(persistedInventory.getItemCount() - numberOfOrderedItems);

                /*commit transaction*/
                transaction.commit();

                return true;

            }
        }
    }

    public boolean signDeliveredOrder(long itemOrderId){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*begin the signing process.
                * check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                try
                {
                    /*get the item order by id*/
                    ItemOrder itemOrder = manager.createQuery(
                            "select itemOrder from ItemOrder itemOrder where id=:itemOrderId", ItemOrder.class
                    ).setParameter("itemOrderId", itemOrderId).getSingleResult();

                    /*sign it off by setting the status to complete*/
                    itemOrder.setStatus("completed");

                    /*commit the transaction*/
                    transaction.commit();

                    return true;

                }
                catch (Exception exception){

                    /*return false on failure*/
                    return false;
                }
            }
        }
    }

    public boolean checkOrders(){

        /*define pending status*/
        String pendingStatus = "pending";
        String dispatchedStatus = "dispatched";
        String completedStatus = "completed";

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the entity transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*create the query for pending orders*/
                this.pendingOrders = manager.createQuery(
                        "select itemOrder from ItemOrder itemOrder where status=:pendingStatus", ItemOrder.class
                ).setParameter("pendingStatus", pendingStatus).getResultList();

                /*create a query for dispatched orders.*/
                this.dispatchedOrders = manager.createQuery(
                        "select itemOrder from ItemOrder itemOrder where status=:dispatchedStatus", ItemOrder.class
                ).setParameter("dispatchedStatus", dispatchedStatus).getResultList();

                /*create a query for completed orders*/
                this.completedOrders = manager.createQuery(
                        "select itemOrder from ItemOrder itemOrder where status=:completedStatus", ItemOrder.class
                ).setParameter("completedStatus", completedStatus).getResultList();

                /*commit the transaction*/
                transaction.commit();

                return true;

            }
        }
    }

    public String getPERSISTENCE_UNIT() {
        return PERSISTENCE_UNIT;
    }

    public List<ItemOrder> getPendingOrders() {
        return pendingOrders;
    }

    public List<ItemOrder> getDispatchedOrders() {
        return dispatchedOrders;
    }

    public List<ItemOrder> getCompletedOrders() {
        return completedOrders;
    }

    /**
     * testing */
    public static void main(String[] args) {

        /*construct the order management service*/
        OrderManagementService service = new OrderManagementService();

        /*dispatch order*/
        if (!service.signDeliveredOrder(52)) {
            System.out.println("error occurred!");
            return;
        }

        System.out.println("completed successfully");

    }
}
