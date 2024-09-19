package com.project.BDs;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Set;

import java.util.HashSet;

@Entity
@Table (name="pizzas")
public class Pizzas {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id_p")
    private Long id;

    @Column (name = "name_p")
    private String name;

    @Column (name="cost_p")
    private int cost;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name="ingredients_in_pizzas",
            joinColumns = {@JoinColumn(name="id_p")},
            inverseJoinColumns = {@JoinColumn(name="id_i")}
    )
    private Set<Ingredients> ingredientsSet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_img")
    private Images image = new Images();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Set<Ingredients> getIngredientsSet(){
        return ingredientsSet;
    }

    public Images getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setIngredientsSet(Set<Ingredients> ingredientsSet) {
        this.ingredientsSet = ingredientsSet;
    }

    public void setImage(Images image) {
        this.image = image;
    }

    @PreRemove
    private void removeIngredientsSet() {
        getIngredientsSet().clear();
    }
}
