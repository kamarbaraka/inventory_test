/*
 * Copyright (c) 2023. This code is protected under the GPL 2.0 license.
 */

package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * a class to simulate an address.
 * @author kamar baraka*/

@Entity
public class Address {

    @Id
    @GeneratedValue
    private long id;
    private String street;
    private String  city;
    private String country;

    private String zipcode;

    public Address() {
    }

    public Address(String street, String city, String country, String zipcode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
