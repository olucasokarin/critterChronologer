package com.udacity.jdnd.course3.critter.user.vo;

import com.udacity.jdnd.course3.critter.pet.vo.Pet;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    private String notes;

    @OneToMany(mappedBy = "customer", targetEntity = Pet.class, cascade = CascadeType.ALL)
    private List<Pet> pets;


    public Customer() {
    }

    public void addPet(Pet pet) {
        if(pets == null)
            pets = new ArrayList<>();
        pets.add(pet);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

