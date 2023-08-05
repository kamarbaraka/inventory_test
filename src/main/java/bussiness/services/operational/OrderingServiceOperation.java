package bussiness.services.operational;

import bussiness.services.management.InventoryManagementService;
import bussiness.services.management.OrderManagementService;
import bussiness.services.tracking.InventoryTrackingService;
import persistence.*;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *  a class to simulate the ordering process.
 *  @author kamar baraka.*/

public class OrderingServiceOperation {

    public boolean start(){

        /*construct scanner to get user input*/
        Scanner input = new Scanner(System.in);

        /*construct an inventory management service*/
        InventoryManagementService inventoryManagementService = new InventoryManagementService();

        /*construct an order management service*/
        OrderManagementService orderManagementService = new OrderManagementService();

        /*construct a payment processing service*/
        PaymentProcessingService paymentProcessingService = new PaymentProcessingService();

        /*begin operation*/
        MAIN:
        while (true) {

            /*get the customer details.
            * get the name */
            System.out.println("enter your name;");
            String customerName = input.next();

            /*get the contact*/
            System.out.println("enter your contact;");
            String customerContact = input.next();

            /*get the customer address*/
            System.out.println("enter the street you live in;");
            String customerStreet = input.next();

            System.out.println("enter the city you live;");
            String customerCity = input.next();

            System.out.println("enter the country of residence;");
            String customerCountry = input.next();

            System.out.println("enter your zipcode;");
            String customerZipcode = input.next();

            /*construct the address*/
            Address customerAddress = new Address();
            customerAddress.setStreet(customerStreet);
            customerAddress.setCity(customerCity);
            customerAddress.setCountry(customerCountry);
            customerAddress.setZipcode(customerZipcode);

            /*construct the customer*/
            Customer customer = new Customer();
            customer.setCustomerName(customerName);
            customer.setCustomerContact(customerContact);
            customer.setAddress(customerAddress);

            /*declare an item inventory and the item to order*/
            ItemInventory itemInventory;

            String itemToOrder;

            ITEM_TO_ORDER:
            while (true)
            {
                /*ask for item to order*/
                System.out.println("enter the item you wish to order");
                itemToOrder = input.next().strip();

                /*search for the item*/
                itemInventory = inventoryManagementService.searchInventory(itemToOrder);

                if (itemInventory == null) {

                    /*notify the user and continue*/
                    System.out.println("no such item in the inventory! continue? (Y or N)");
                    String response = input.next();

                    switch (response) {

                        case "N", "n" -> {
                            break MAIN;
                        }
                        case "y", "Y" -> {
                            continue ITEM_TO_ORDER;
                        }
                        default -> System.out.println("invalid response!");
                    }

                }

                /*check for the count in the inventory*/
                if (itemInventory.getItemCount() == 0) {

                    /*notify the user and continue*/
                    System.out.println("item is out of stock!, continue? (Y or N)");
                    String response = input.next();

                    switch (response){

                        case "Y", "YES", "y", "yes" -> {continue ITEM_TO_ORDER;}
                        case "N", "NO", "n", "no" -> {break MAIN;}
                        default -> System.out.println("invalid input");
                    }
                }
                break ITEM_TO_ORDER;
            }

            /*display the item details.
            * get the item and price*/
            Item orderedItem = itemInventory.getItem();
            BigDecimal itemPrice = orderedItem.getPrice();
            System.out.printf("name: %s, price: %s %n proceed?(Y or N)%n", orderedItem.getItemName(), orderedItem.getPrice());
            String customerResponse = input.next();

            /*break if canceled*/
            if (customerResponse.equals("N"))
                break MAIN;

            /*ask for the number of items to order*/
            int numberOfItemsToOrder;
            while (true) {

                System.out.printf("enter the number of %s you wish to order;%n", itemToOrder);
                numberOfItemsToOrder = input.nextInt();

                /*check if possible to order such amount*/
                if (numberOfItemsToOrder > itemInventory.getItemCount()){

                    /*notify the user and continue*/
                    System.out.printf("the maximum you can order is %d, proceed? (Y or N) %n", itemInventory.getItemCount());
                    String response = input.next();

                    switch (response){

                        case "Y", "y", "Yes", "yes", "YES" -> {continue;}
                        case "N", "n", "No", "no", "NO" -> {break MAIN;}
                        default -> System.out.println("invalid input");
                    }

                }
                break;
            }

            /*compute the amount to pay*/
            BigDecimal amountToPay = itemPrice.multiply(BigDecimal.valueOf(3));

            /*notify the customer*/
            System.out.printf("total amount to pay %s %n proceed?(Y or N) %n", amountToPay);
            String response = input.next();

            if (response.equals("N"))
                break;

            /*declare payment*/
            Payment payment;

            PAYMENT:
            while (true)
            {
                /*request for the payment method*/
                System.out.println("enter the mode of payment;");
                System.out.println("1. CASH, 2. BANK, 3. MOBILE");
                response = input.next();

                /*process the payment*/
                switch (response) {

                    case "1", "CASH", "cash" -> {
                        payment = paymentProcessingService.cashPayment(amountToPay);
                        break PAYMENT;
                    }
                    case "2", "BANK", "bank" -> {
                        payment = paymentProcessingService.bankPayment(amountToPay);
                        break PAYMENT;
                    }
                    case "3", "MOBILE", "mobile" -> {
                        payment = paymentProcessingService.mobilePayment(amountToPay);
                        break PAYMENT;
                    }
                    default -> System.out.println("invalid input!");
                }
            }

            /*construct a bill*/
            Bill bill = new Bill();
            bill.setCustomer(customer);
            bill.setPayment(payment);
            bill.setItems(orderedItem);

            /*construct the order*/
            ItemOrder itemOrder = new ItemOrder();
            itemOrder.setOrderedItem(orderedItem);
            itemOrder.setBill(bill);
            itemOrder.setStatus("pending");

            /*add the order to the system.
            * construct an order management service*/
            boolean orderResponse = orderManagementService.addOrder(customerAddress, payment, customer, bill, itemOrder);

            /*notify the customer of successful order*/
            if (!orderResponse){

                System.out.println("order failed!");
                return false;
            }

            System.out.println("order successful");
            System.out.printf("your order ID is %s %n", itemOrder.getId());

            /*reduce inventory count*/
            if (!orderManagementService.updateCount(itemInventory, numberOfItemsToOrder)){

                /*notify the user and continue*/
                System.out.println("an error occurred!");
                continue;
            }

            break ;

        }

        return true;
    }

    public static void main(String[] args) {

        /*construct an order service*/
        OrderingServiceOperation serviceOperation = new OrderingServiceOperation();

        /*start the service*/
        serviceOperation.start();

    }

}
