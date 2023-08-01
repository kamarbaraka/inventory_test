package bussiness.services.management;

import jakarta.persistence.*;
import persistence.Item;
import persistence.ItemInventory;
import persistence.ItemLocation;

/**
 *  a class to simulate stock management.
 *  @author kamar baraka.*/

public class StockManagementService {

    private final String PERSISTENCE_UNIT = "inventory_management";

    public boolean addStock(Item item, ItemLocation location, ItemInventory inventory){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*check if the item exists*/
                try{

                    /*create a query for item*/
                    ItemInventory persistedItemInventory = manager.createQuery(
                            "select itemInventory from ItemInventory itemInventory where inventoryName=:name",
                            ItemInventory.class
                    ).setParameter("name", item.getItemName()).getSingleResult();

                    /*update the count*/
                    persistedItemInventory.setCount(persistedItemInventory.getCount() + inventory.getCount());

                    /*commit transaction*/
                    transaction.commit();
                    return true;

                }
                catch (NoResultException exception){

                    /*check and begin transaction*/
                    if (!transaction.isActive())
                        transaction.begin();

                    /*persist operation*/
                    manager.persist(item);
                    manager.persist(location);
                    manager.persist(inventory);

                    /*commit transaction*/
                    transaction.commit();

                    return true;
                }
            }
        }
    }
}
