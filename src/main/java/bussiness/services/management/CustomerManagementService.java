package bussiness.services.management;

import jakarta.persistence.*;
import persistence.Customer;

import java.awt.*;
import java.util.Scanner;

/**
 * a class to manage a customer.
 * @author kamar baraka.*/

public class CustomerManagementService {

    /**
     * start the service*/
    public String start(){
        /*implement operations*/

        /*construct an entity manager factory*/
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                "inventory_management"
        );

        /*declare the customer to manage*/
        Customer customer;

        try {

            /*construct an entity manager*/
            EntityManager manager = factory.createEntityManager();

            /*get transaction from the manager*/
            EntityTransaction transaction = manager.getTransaction();

            /*construct a scanner and attach input stream*/
            Scanner input = new Scanner(System.in);

            /*perform operations*/
            START:
            while (true){

                /*prompt the user of this service*/
                System.out.println("enter the customer name");
                String inputCustomerName = input.next();
                /*if response is exit, exit*/
                if (inputCustomerName.equals("exit"))
                    break START;

                /*get the customer by the name*/
                try {

                    /*check and begin transaction*/
                    if (!transaction.isActive())
                        transaction.begin();

                    customer = manager.createQuery(
                            "select customer from Customer customer where customerName=:inputCustomerName", Customer.class
                    ).setParameter("inputCustomerName", inputCustomerName).getSingleResult();

                    CHANGE:
                    while (true){

                        /*prompt the user */
                        System.out.println("1. Change  name");
                        System.out.println("2. Change contact");

                        /*perform action based on response*/
                        switch (input.next()){

                            case "1" -> {
                                CASE1:
                                while (true)
                                {
                                    System.out.println("enter the new name");
                                    /*get the new name*/
                                    String name = input.next();

                                    /*change the name*/
                                    try {

                                        customer.setCustomerName(name);

                                        /*commit the transaction*/
                                        transaction.commit();

                                    }
                                    catch (Exception exception) {

                                        /*prompt the user*/
                                        System.out.println("failed");
                                        System.out.println("what do you wish to do");
                                        System.out.println("1. Try Again");
                                        System.out.println("2. Exit");

                                        /*get response*/
                                        switch (input.next()){

                                            case "1" -> {continue CASE1;}
                                            case "2" -> {continue CHANGE;}
                                            default -> {
                                                Toolkit.getDefaultToolkit().beep();
                                                System.out.println("invalid option");
                                            }
                                        }

                                    }
                                }
                            }
                            case "2" -> {

                                /*begin operation*/
                                CASE2:
                                while (true){

                                    /*prompt the user*/
                                    System.out.println("enter the new contact");
                                    /*save the response*/
                                    String newContact = input.next();

                                    /*change the contact*/
                                    try {

                                        customer.setCustomerContact(newContact);

                                        /*commit the transaction*/
                                        transaction.commit();
                                    }
                                    catch (Exception exception){

                                        /*prompt the user*/
                                        System.out.println("failed");
                                        System.out.println("what do you wish to do");
                                        System.out.println("1. Try Again");
                                        System.out.println("2. Exit");

                                        /*get response*/
                                        switch (input.next()){

                                            case "1" -> {continue CASE2;}
                                            case "2" -> {continue CHANGE;}
                                            default -> {
                                                Toolkit.getDefaultToolkit().beep();
                                                System.out.println("invalid option");
                                            }
                                        }

                                    }
                                }

                            }
                            default -> {
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("invalid option");
                            }
                        }

                    }


                }
                catch (NoResultException exception){
                    /*prompt the user*/
                    System.out.println(" no such customer ");

                    while(true)
                    {
                        System.out.println(" what do you wish to do");
                        System.out.println("1. Try Again");
                        System.out.println("2. Exit");


                        /*get the response and perform operation based on the response*/
                        switch (input.next()) {

                            case "1" -> {continue START;}
                            case "2" -> {break START;}
                            default -> {
                                Toolkit.getDefaultToolkit().beep();
                                System.out.println("invalid choice");
                            }
                        }
                    }

                }

            }

        }
        finally {
            /*check and close the factory*/
            if (factory.isOpen())
                factory.close();
        }

        return "success";
    }

    /**
     * test the service*/
    public static void main(String[] args) {

        /*construct a customer management service*/
        CustomerManagementService service = new CustomerManagementService();

        /*start the service and print the response*/
        System.out.println(service.start());

    }
}
