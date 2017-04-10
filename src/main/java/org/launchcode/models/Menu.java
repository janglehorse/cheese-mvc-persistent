package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by adminbackup on 4/10/17.
 */
@Entity
public class Menu {

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @Id
    @GeneratedValue
    private int Id;

    @ManyToMany
    private List<Cheese> cheeses;

    public Menu() {

    }

    public Menu(String name){

        setName(name);
    }

    public void addItem(Cheese item){

        cheeses.add(item);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

}
