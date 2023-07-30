package persistence;

import jakarta.persistence.*;


/**
 * a class to simulate an inventory.
 * @author kamar baraka*/

@Entity
public class ItemInventory {

    @Id
    private String inventoryName;

    @ManyToOne
    private Item item;

    private int count;

    @OneToOne
    private ItemLocation itemLocation;

    public void setCount(int count) {
        this.count = count;
    }

    public void addItem(Item item){

        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public int getCount() {
        return count;
    }

    public ItemLocation getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(ItemLocation itemLocation) {
        this.itemLocation = itemLocation;
    }
}
