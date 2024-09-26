package org.test.expensesservice.entities;

import org.springframework.data.rest.core.config.Projection;
import org.test.expensesservice.models.Category;

@Projection(types = Expense.class,name = "expenseProjection")
public interface ExpenseProjection {
    Long getId();
    String getDescription();
    double getAmount();
    Category getCategory(); // This assumes a method in Category to retrieve the name
}
