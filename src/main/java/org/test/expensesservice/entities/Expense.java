package org.test.expensesservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.test.expensesservice.models.Category;

import java.util.List;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Expense extends RepresentationModel<Expense> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double amount;
    @Transient
    private Category category;
    private Long categoryId;
    // Getters and Setters
}
