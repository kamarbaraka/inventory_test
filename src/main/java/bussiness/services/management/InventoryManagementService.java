package bussiness.services.management;

import jakarta.persistence.*;
import persistence.Item;
import persistence.ItemInventory;

import java.awt.*;

/**
 *  a class to simulate an inventory.
 *  @author kamar baraka.*/

public class InventoryManagementService {

    public boolean restock(Item item, int count){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("inventory_management")) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*construct an entity transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin the transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*check if the item exists in the inventory*/
                try {

                    /*create a query*/
                    ItemInventory inventory = manager.createQuery(
                            "select inventory from ItemInventory inventory where inventoryName=:itemName",
                            ItemInventory.class).setParameter("itemName", item.getItemName()).getSingleResult();

                    /*update count*/
                    inventory.setCount(inventory.getCount() + count);

                    /*commit transaction*/
                    transaction.commit();

                }
                catch (NoResultException exception){

                    /*notify user*/
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("no such item in the inventory");
                }

            }
        }

        return true;
    }

}
