package bussiness.services.operational;

import jakarta.persistence.*;
import persistence.User;

import java.util.Scanner;

/**
 * a class to simulate the login service.
 * @author kamar baraka.*/

public class LoginServiceOperation {

    /**
     * sign in a user.
     * @return the user logged in*/
    public User login(){

        /*construct an entity manager factory from which construct an entity manager*/
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(
                "inventory_management"
        );

        /*declare a user object*/
        User user;

        try {

            /*construct an entity manager from the factory*/
            EntityManager manager = factory.createEntityManager();

            /*get transaction from the manager*/
            EntityTransaction transaction = manager.getTransaction();

            /*construct a scanner to listen for user input and attach the standard input stream to it*/
            Scanner input = new Scanner(System.in);


            /*login operation*/
            while (true){

                /*get the username*/
                System.out.println("enter your username");
                String userName = input.next();

                /*get the user password*/
                System.out.println("enter the password");
                String userPassword = input.next();

                /*validate the credentials*/

                /*check the username*/
                try{

                    /*check and connect to the persistence unit*/
                    if (!transaction.isActive())
                        transaction.begin();

                    /*query*/
                    manager.createQuery(
                            "select username from User user where username=:userName", String.class
                    ).setParameter("userName", userName).getSingleResult();

                    /*commit the query*/
                    transaction.commit();

                    /*check the user password*/

                    /*check and connect to the persistence unit*/
                    if (!transaction.isActive())
                        transaction.begin();

                    /*query, try as multiple users might have the same password*/
                    try
                    {
                        manager.createQuery(
                                "select password from User user where password=:userPassword", String.class
                        ).setParameter("userPassword", userPassword).getSingleResult();
                    }
                    catch (NoResultException ignored){}

                    /*commit the query*/
                    transaction.commit();

                    /*check and open a connection to the database*/
                    if (!transaction.isActive())
                        transaction.begin();

                    /*initialize the user object with the user from the database*/
                    user = manager.createQuery(
                            "select user from User user where username=:userName", User.class
                    ).setParameter("userName", userName).getSingleResult();

                    /*break when all queries are successful*/
                    break;

                }
                catch (NoResultException exception){

                    /*notify the user*/
                    System.out.println("incorrect username or password");

                }

            }

        }
        finally {
            if (factory.isOpen())
                factory.close();
        }

        /*return the user object upon successful login*/
        return user;
    }

    /**
     * test the login service*/
    public static void main(String[] args) {

        /*construct the login service*/
        LoginServiceOperation service = new LoginServiceOperation();

        /*run the service and print the user role*/
        User user = service.login();
        System.out.println(user.getRole());
    }
}
