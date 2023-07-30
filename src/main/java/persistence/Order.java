package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 *  a class to simulate an order.
 *  @author kamar baraka.*/

@Entity
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Item orderedItem;

    private String status;

    public Item getOrderedItem() {
        return orderedItem;
    }

    public void setOrderedItem(Item orderedItem) {
        this.orderedItem = orderedItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

}