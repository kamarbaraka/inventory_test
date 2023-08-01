package bussiness.services.operational;

import bussiness.services.management.UserManagementService;
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

        /*declare a user object*/
        User user;

        /*construct a user management service*/
        UserManagementService userManagementService = new UserManagementService();

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
            user = userManagementService.checkCredentials(userName, userPassword);

            if (user != null){

                /*break if successful*/
                break;
            }

            /*notify the user*/
            System.out.println("incorrect username or password");

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
