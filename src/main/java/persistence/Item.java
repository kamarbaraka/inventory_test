package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/**
 * a class to simulate an item.
 * @author kamar baraka*/

@Entity
public class Item {

    @Id
    @GeneratedValue
    private long id;
    private String itemName;
    @GeneratedValue
    private int serialNumber;
    private BigDecimal price;



    /**
     * get the name of the item.
     * @return the name of the item*/
    public String getItemName() {
        return itemName;
    }

    /**
     * set the name of the item.*/
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * get the price of the item.
     * @return the price of the item*/
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * sets the price of the item*/
    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", serialNumber=" + serialNumber +
                ", price=" + price +
                '}';
    }
}
