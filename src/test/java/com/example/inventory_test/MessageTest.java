package com.example.inventory_test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import org.eclipse.yasson.JsonBindingProvider;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void messagePersistenceTest(){

        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("inventory_management")) {

            EntityManager manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            Message message = new Message();
            message.setTheMessage("hello, kamar");

            transaction.begin();

            manager.persist(message);

            transaction.commit();
        }
    }

}