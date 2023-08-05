package bussiness.services.tracking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import persistence.ItemInventory;

import java.awt.*;
import java.util.List;

/**
 *  a class to simulate an inventory tracker.
 *  @author kamar baraka.*/

public class InventoryTrackingService implements Runnable{

    private final String PERSISTENCE_UNIT = "inventory_management";

    @Override
    public void run() {

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager */
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*continuously check and notify when inventory count is low.*/
                try
                {
                    while (true) {

                        /*create query*/
                        List<ItemInventory> itemInventories = manager.createQuery(
                                "select itemInventory from ItemInventory itemInventory where itemCount<5",
                                ItemInventory.class
                        ).getResultList();

                        /*report if less*/
                        Toolkit.getDefaultToolkit().beep();
                        itemInventories.forEach(itemInventory -> System.err.printf("%s ", itemInventory.getInventoryName()));

                        /*wait for 2 mins before the next check*/
                        Thread.sleep(12000);

                    }
                }
                catch (InterruptedException exception){

                    /*ignore*/
                }
            }
        }
    }

    /**
     * setting up the service*/
    public static Thread setup(){
        /*construct the tracking service*/
        InventoryTrackingService service = new InventoryTrackingService();

        /*construct a thread for the service*/
        return new Thread(service);
    }

}
