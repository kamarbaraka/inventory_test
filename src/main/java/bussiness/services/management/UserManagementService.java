package bussiness.services.management;

import jakarta.persistence.*;
import persistence.Address;
import persistence.Role;
import persistence.User;

/**
 *  a class to simulate a user management service.
 *  @author kamar baraka.*/

public class UserManagementService {

    private final String  PERSISTENCE_UNIT = "inventory_management";

    public boolean checkUserName(String userName){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*check if the username exists*/
                try {

                    /*create a query*/
                    manager.createQuery(
                            "select username from User user where username=:userName", String.class
                    ).setParameter("userName", userName).getSingleResult();

                    /*commit transaction*/
                    transaction.commit();

                    if (factory.isOpen())
                        factory.close();
                    return true;

                }
                catch (NoResultException exception){

                    if (factory.isOpen())
                        factory.close();

                    /*return false if user doesn't exist*/
                    return false;
                }

            }

        }
    }

    public boolean addUser(Address address, Role role, User user){

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*check and begin transaction*/
                if (!transaction.isActive())
                    transaction.begin();

                /*persist the user*/
                try {

                    manager.persist(address);
                    manager.persist(role);
                    manager.persist(user);

                    /*commit transaction*/
                    transaction.commit();
                    return true;

                }
                catch (Exception exception){

                    /*return false on failure*/
                    return false;
                }
            }
        }
    }

    public User checkCredentials(String userName, String passWord){

        /*declare the user*/
        User user;

        /*construct an entity manager factory*/
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            /*construct an entity manager*/
            try (EntityManager manager = factory.createEntityManager()) {

                /*get the transaction*/
                EntityTransaction transaction = manager.getTransaction();

                /*query for user*/
                try
                {
                    user = manager.createQuery(
                            "select user from User user where username=:userName and password=:passWord", User.class
                    ).setParameter("userName", userName).setParameter("passWord", passWord).getSingleResult();
                }
                catch (NoResultException exception){

                    /*nullify on failure*/
                    user = null;
                }

            }

        }

        return user;
    }
}
