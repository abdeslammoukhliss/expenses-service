package org.test.expensesservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Repository;
import org.test.expensesservice.entities.Expense;
import org.test.expensesservice.entities.ExpenseProjection;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = ExpenseProjection.class)
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
        List<Expense> findExpenseByCategoryId(Long categoryId);
}
