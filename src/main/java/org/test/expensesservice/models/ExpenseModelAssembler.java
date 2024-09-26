package org.test.expensesservice.models;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.test.expensesservice.controllers.ExpenseController;
import org.test.expensesservice.entities.Expense;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ExpenseModelAssembler extends RepresentationModelAssemblerSupport<Expense, Expense> {

    public ExpenseModelAssembler() {
        super(ExpenseController.class, Expense.class);
    }

    @Override
    public Expense toModel(Expense expense) {
        expense.add(linkTo(methodOn(ExpenseController.class).getExpenseById(expense.getId())).withSelfRel());
        expense.add(linkTo(methodOn(ExpenseController.class).getAllExpenses()).withRel("expenses"));
        return expense;
    }
}