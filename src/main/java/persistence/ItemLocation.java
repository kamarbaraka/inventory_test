/*
 * Copyright (c) 2023. This code is protected under the GPL 2.0 license.
 */

package persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * a class to represent an item location.
 * @author kamar baraka*/

@Entity
public class ItemLocation{

    @Id
    @GeneratedValue
    private long id;

    private String section;

    private int col;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int column) {
        this.col = column;
    }
}