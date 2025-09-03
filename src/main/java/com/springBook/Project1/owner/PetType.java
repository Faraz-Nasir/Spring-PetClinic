package com.springBook.Project1.owner;

import com.springBook.Project1.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="types")
public class PetType extends NamedEntity {
}
