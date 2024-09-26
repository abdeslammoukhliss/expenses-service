package org.test.expensesservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data @NoArgsConstructor @AllArgsConstructor
public class Category extends RepresentationModel<Category> {
    private Long id;
    private String name;

    // Getters and Setters
}
