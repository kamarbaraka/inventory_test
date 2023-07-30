package bussiness.services.operational;

import bussiness.services.management.InventoryManagementService;
import jakarta.persistence.*;
import persistence.Item;
import persistence.ItemInventory;
import persistence.ItemLocation;

import java.awt.*;
import java.util.Scanner;

/**
 *  a class to perform stock purchase operation.
 *  @author kamar baraka.*/

public class StockPurchaseServiceOperation {

    public static boolean run(){

        /*construct a scanner to get user input*/
        Scanner input = new Scanner(System.in);

        boolean end = true;
        while (end){

            /*get the name of the item*/
            System.out.println("enter the name of the item");
            String inputItemName = input.next();

            /*get the price*/
            System.out.println("enter the price");
            double inputPrice = input.nextDouble();

            /*get the count*/
            System.out.println("enter the number of items");
            int inputCount = input.nextInt();

            /*get the location*/
            System.out.println("enter the section to store the item");
            String section = input.next();

            /*get the row number*/
            System.out.println("enter the column number to store the item");
            int inputColumn = input.nextInt();


            /*construct an entity manager factory*/
            try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                    "inventory_management")) {

                /*construct an entity manager*/
                try (EntityManager manager = factory.createEntityManager()) {

                    /*construct an entity transaction*/
                    EntityTransaction transaction = manager.getTransaction();


                    /*check and begin the transaction*/
                    if (!transaction.isActive())
                        transaction.begin();

                    /*construct the item*/
                    Item item = new Item();
                    item.setItemName(inputItemName);
                    item.setPrice(inputPrice);

                    /*check for the existence of the item in the inventory*/
                    try {
                        ItemInventory persistedItemInventory = manager.createQuery(
                                "select inventory from ItemInventory inventory where inventoryName=:inputItemName", ItemInventory.class
                        ).setParameter("inputItemName", inputItemName).getSingleResult();

                        /*update counts*/
                        InventoryManagementService service = new InventoryManagementService();
                        service.restock(item, inputCount);

                    }
                    catch (NoResultException exception) {/*construct item location*/
                        ItemLocation location = new ItemLocation();
                        location.setSection(section);
                        location.setCol(inputColumn);

                        /*construct inventory*/
                        ItemInventory inventory = new ItemInventory();
                        inventory.setInventoryName(inputItemName);
                        inventory.setItemLocation(location);
                        inventory.setCount(inputCount);

                        /*add number of items to inventory*/
                        inventory.addItem(item);

                        /*persist the entities*/
                        manager.persist(item);
                        manager.persist(location);
                        manager.persist(inventory);

                        /*commit the transaction*/
                        transaction.commit();
                    }

                }
            }

            System.out.println("do you wish to add more stock? (Y or N)");
            switch (input.next()){

                case "N", "n" -> {break;}

                case "Y", "y" -> {continue;}

                default -> {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("invalid input");}
            }

            end = !end;
            break;
        }

        return true;

    }

    public static void main(String[] args) {

        /*construct the service*/

        StockPurchaseServiceOperation.run();
    }
}
