package com.example.inventory_test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 *  a class to simulate a message.
 *  @author kamar baraka*/

@Entity
public class Message {

    @Id
    @GeneratedValue
    private long id;

    private String theMessage;

    public String getTheMessage() {
        return theMessage;
    }

    public void setTheMessage(String theMessage) {
        this.theMessage = theMessage;
    }
}
