package com.project.BDs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.BDs.Services.FileManager;
import jakarta.persistence.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name="images")
public class Images {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column (name = "id_img")
    private Long id;

    @Column (name = "name_img")
    private String name;

    @Column (name="size_img")
    private long size;

    @Column (name="key_img")
    private String key;

    @Column (name="date_upload_img")
    private LocalDate dateUpload;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "image", cascade = CascadeType.ALL)
    private Set<Pizzas> pizzasSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getKey() {
        return key;
    }

    public LocalDate getDateUpload() {
        return dateUpload;
    }
//
//    public Set<Pizzas> getPizzasSet() {
//        return pizzasSet;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDateUpload(LocalDate dateUpload) {
        this.dateUpload = dateUpload;
    }

    public void setPizzasSet(Set<Pizzas> pizzasSet) {
        this.pizzasSet = pizzasSet;
    }

    @PreRemove
    private void deleteImageFile() throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.delete(this.key);
    }
}
