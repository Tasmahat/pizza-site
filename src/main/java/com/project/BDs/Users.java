package com.project.BDs;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table (name="users")
public class Users {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id_u")
    private Long id;

    @Column (name = "name_u")
    private String name;

    @Column (name = "email_u")
    private String email;

    @Column (name = "can_add")
    private boolean canAddPizza;

    @Column (name = "can_delete")
    private boolean canDelPizza;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean getCanAddPizza() {
        return canAddPizza;
    }

    public boolean getCanDelPizza() {
        return canDelPizza;
    }

    public String getRole() {
        if(canAddPizza) {
            if(canDelPizza) {
                return "ADMIN";
            } else {
                return "ADDER";
            }
        } else {
            if(canDelPizza) {
                return "DELETER";
            } else {
                return "USER";
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
