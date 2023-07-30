package persistence;

import com.sun.istack.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.util.Date;

/**
 * a class to simulate a user and properties.
 * @author kamar baraka*/

@Entity
public class User {

    @Id
    @NotNull
    private String username;

    private String firstName;

    private String lastName;

    private String contact;


    @OneToOne
    private Role role;

    private String password;


    @ManyToOne
    private Address address;

    private final Date dateOfRegistration = new Date();

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    /**
     * gets the user password.
     * @return the user password.*/
    public String getPassword() {
        return password;
    }

    /**
     * sets the user password.
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the user address.
     * @return the user address*/
    public Address getAddress() {
        return address;
    }

    /**
     * sets the user address*/
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
