package com.springBook.Project1.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@MappedSuperclass
@Data
public class NamedEntity extends BaseEntity {

    @Column(name="name")
    @NotBlank
    private String name;


}
