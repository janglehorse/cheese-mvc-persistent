package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by adminbackup on 4/6/17.
 */
@Entity
public class Category {

    @Id
    @GeneratedValue
    private int Id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
