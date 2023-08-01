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

            }

        }

        return true;
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
                }
                catch (Exception exception){

                    /*return false if fail*/
                    return false;
                }

            }

        }

        return true;
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

                }
                catch (Exception exception){

                    /*return false on fail*/
                    return false;
                }

            }

        }

        return true;
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
                persistedInventory.setCount(persistedInventory.getCount() - numberOfOrderedItems);

                /*commit transaction*/
                transaction.commit();

                return true;

            }
        }
    }
}
