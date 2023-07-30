package persistence;


import jakarta.persistence.*;

/**
 * a class to represent a customer and properties.
 * @author kamar baraka*/

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    private String customerName;

    private String customerContact;

    @ManyToOne
    private Address address;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
