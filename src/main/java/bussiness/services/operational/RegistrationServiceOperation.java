package bussiness.services.operational;

import bussiness.roles.*;
import jakarta.persistence.*;
import persistence.Address;
import persistence.Role;
import persistence.User;

import java.util.Scanner;

/**
 * a class to simulate the registration process.
 * @author kamar baraka*/

public class RegistrationServiceOperation {

    /**
     * register a user.
     * @return "success" when successful*/
    public String run(){

        /*construct a scanner and attach the standard input stream to it*/
        Scanner input = new Scanner(System.in);

        /*construct an address for the user as part of the requirement*/
        Address userAddress = new Address();

        /*construct a user and fill it with user details from the input*/
        User user = new User();

        /*perform transaction with the user*/

        /*construct an entity manager factory from which create entity manager*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                "inventory_management"
        ))
        {
            /*construct an entity manager from the factory*/

            EntityManager manager = factory.createEntityManager();

            /*get transaction from the manager*/
            EntityTransaction transaction = manager.getTransaction();

            /*loop to listen for user input*/

            while (true) {

                /*extract data from user to persist it
                * get the username*/
                System.out.println("enter username");
                String userName = input.next();

                /*check if the username exists*/
                try
                {
                    /*check and begin transaction with the database*/
                    if (!transaction.isActive())
                        transaction.begin();

                    manager.createQuery(
                            "select username from User user where username=:userName",
                            String.class).setParameter("userName", userName).getSingleResult();

                    /*execute the query*/
                    transaction.commit();
                    System.out.println("username is already taken");
                    continue;
                }
                catch (NoResultException ignored){
                }

                /*set the username*/
                user.setUsername(userName);

                /*get and ste the first name*/
                System.out.println("enter your first name");
                String firstName = input.next();
                user.setFirstName(firstName);

                /*get and set the last name*/
                System.out.println("enter your last name");
                String lastName = input.next();
                user.setLastName(lastName);

                /*get and set the contact */
                System.out.println("enter your email address");
                String contact = input.next();
                user.setContact(contact);

                /*get and set the user role*/
                ROLE:
                while (true)
                {
                    System.out.println("enter the role (ADMIN, CASHIER, ACCOUNTANT, TELLER, USER)");
                    String userRole = input.next();
                    switch (userRole) {
                        case "ADMIN" -> {
                            user.setRole(AdminRole.getInstance());
                            break ROLE;
                        }
                        case "CASHIER" -> {
                            user.setRole(CashierRole.getInstance());
                            break ROLE;
                        }
                        case "ACCOUNTANT" -> {
                            Role role = AccountantRole.getInstance();
                            user.setRole(role);
                            break ROLE;
                        }
                        case "TELLER" -> {
                            user.setRole(TellerRole.getInstance());
                            break ROLE;
                        }
                        case "USER" -> {
                            user.setRole(UserRole.getInstance());
                            break ROLE;
                        }
                        default -> {
                            System.out.println("no such role");
                        }
                    }
                }

                /*get and set the password */
                System.out.println("enter your password");
                String password = input.next();
                user.setPassword(password);

                /*get the address of the user.
                * get and set the street */
                System.out.println("enter the street you live in");
                String street = input.next();
                userAddress.setStreet(street);

                /*get and set the city*/
                System.out.println("enter the city you live in");
                String city = input.next();
                userAddress.setCity(city);

                /*get and set the country*/
                System.out.println("enter the country of residence");
                String country = input.next();
                userAddress.setCountry(country);

                /*get the zipcode*/
                System.out.println("enter zipcode");
                String zipcode = input.next();
                userAddress.setZipcode(zipcode);

                break;
            }

            /*persist the user*/

            /*check and connect to the persistence unit*/
            if (!transaction.isActive())
                transaction.begin();

            /*persist the address*/
            manager.persist(userAddress);

            /*set the address of the user*/
            user.setAddress(userAddress);

            /*persist the role*/
            manager.persist(user.getRole());

            /*persist the user*/
            manager.persist(user);

            /*commit the transaction*/
            transaction.commit();

        }

        /*return success when the method has exited successfully*/
        return "success";
    }

    /**
     * test case*/
    public static void main(String[] args) {

        /*construct a registration service*/
        RegistrationServiceOperation service = new RegistrationServiceOperation();

        /*run the service and assert that the registration is successful*/
        System.out.println(service.run());

    }
}
