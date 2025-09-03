package com.springBook.Project1.owner;

import com.springBook.Project1.model.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="owners")
@Data
public class Owner extends Person {

    @Column(name="address")
    @NotBlank
    private String address;

    @Column(name="city")
    @NotBlank
    private String city;

    @Column(name="telephone")
    @NotBlank
    @Pattern(regexp="\\d{10}",message = "{telephone invalid}")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    @OrderBy("name")
    private final List<Pet> pets=new ArrayList<>();

    public void addPet(Pet pet){
        if(pet.isNew()){
            getPets().add(pet);
        }
    }
    public Pet getPet(String name){
        return getPet(name,false);
    }
    public Pet getPet(Integer id){
        for(Pet pet:getPets()){
            if(!pet.isNew()){
                Integer compId=pet.getId();
                if(compId.equals(id)){
                    return pet;
                }
            }
        }
        return null;
    }

    public Pet getPet(String name,boolean ignoreNew){
        for(Pet pet:getPets()){
            if(pet.getName().equalsIgnoreCase(name)){
                if(!ignoreNew || !pet.isNew()){
                    return pet;
                }
            }
        }
        return null;
    }

    public void addVisits(Integer petId,Visit visit){
        Assert.notNull(petId,"Pet Identifier must not be null!");
        Assert.notNull(visit,"Visit must not be null");

        Pet pet=getPet(petId);
        Assert.notNull(pet,"Invalid Pet Identifier");

        pet.addVisits(visit);
    }
}
