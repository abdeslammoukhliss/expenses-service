package org.test.expensesservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.test.expensesservice.models.Category;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String description;
    private Double amount;
    private CategoryDTO category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDTO that = (ExpenseDTO) o;
        return id.equals(that.id);
    }
    // Getters and Setters
}
