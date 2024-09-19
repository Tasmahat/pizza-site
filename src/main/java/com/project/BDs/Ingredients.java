package com.project.BDs;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name="ingredients")
public class Ingredients {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column (name = "id_i")
    private Long id;

    @Column (name = "name_i")
    private String name;

    @ManyToMany(mappedBy = "ingredientsSet", fetch = FetchType.LAZY)
    private Set<Pizzas> pizzasSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPizzasSet(Set<Pizzas> pizzasSet) {
        this.pizzasSet = pizzasSet;
    }

}
