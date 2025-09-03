package com.springBook.Project1.owner;

import com.springBook.Project1.model.NamedEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.core.annotation.Order;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="pets")
@Data
public class Pet extends NamedEntity {
    @Column(name="birth_date")
    @DateTimeFormat(pattern="yyyy-MM-DD")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name="type_id")
    private PetType type;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="pet_id")
    @OrderBy("date ASC")
    private final Set<Visit> visits=new LinkedHashSet<>();

    public void addVisits(Visit visit){
        getVisits().add(visit);
    }
}
