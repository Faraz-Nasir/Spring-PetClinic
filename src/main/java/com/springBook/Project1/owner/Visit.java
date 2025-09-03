package com.springBook.Project1.owner;

import com.springBook.Project1.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="Entity")
@Data
public class Visit extends BaseEntity {
    @Column(name="visit_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @NotBlank
    private String description;

    public Visit(){
        this.date=LocalDate.now();
    }
}
