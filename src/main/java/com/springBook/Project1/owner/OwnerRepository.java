package com.springBook.Project1.owner;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner,Integer> {
    Page<Owner> findByLastNameStartingWith(String lastName, Pageable page);
    Optional<Owner> findById(@NotNull Integer id);


}
