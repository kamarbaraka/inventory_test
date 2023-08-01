package bussiness.services.operational;

import bussiness.services.management.InventoryManagementService;
import bussiness.services.management.StockManagementService;
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

        /*construct a stock management service*/
        StockManagementService stockManagementService = new StockManagementService();

        /*construct a scanner to get user input*/
        Scanner input = new Scanner(System.in);

        while (true){

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

            /*construct the item*/
            Item item = new Item();
            item.setItemName(inputItemName);
            item.setPrice(inputPrice);

            /*construct item location*/
            ItemLocation location = new ItemLocation();
            location.setSection(section);
            location.setCol(inputColumn);

            /*construct inventory*/
            ItemInventory inventory = new ItemInventory();
            inventory.setInventoryName(inputItemName);
            inventory.addItem(item);
            inventory.setItemLocation(location);
            inventory.setCount(inputCount);

            /*restock the item*/
            if (!stockManagementService.addStock(item, location, inventory)){

                /*notify the user*/
                System.out.println("an error occurred!");
                continue;
            }

            System.out.println("do you wish to add more stock? (Y or N)");
            switch (input.next()){

                case "Y", "y", "Yes", "yes", "YES" -> {continue;}
                case "N", "n", "No", "no", "NO" -> {break;}
                default -> {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("invalid input");
                    continue;
                }
            }
            break;
        }
        return true;
    }

    public static void main(String[] args) {

        /*construct the service*/

        StockPurchaseServiceOperation.run();
    }
}
