/*
 * Copyright (c) 2023. This code is protected under the GPL 2.0 license.
 */

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

    private int itemCount;

    @OneToOne
    private ItemLocation itemLocation;

    public void setItemCount(int count) {
        this.itemCount = count;
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

    public int getItemCount() {
        return itemCount;
    }

    public ItemLocation getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(ItemLocation itemLocation) {
        this.itemLocation = itemLocation;
    }
}
