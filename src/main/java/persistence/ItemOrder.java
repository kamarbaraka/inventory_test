package persistence;

import jakarta.persistence.*;

/**
 *  a class to simulate an order.
 *  @author kamar baraka.*/

@Entity
public class ItemOrder {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Item orderedItem;

    @OneToOne
    private Bill bill;

    private String status;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

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