package org.test.expensesservice.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.expensesservice.entities.Expense;
import org.test.expensesservice.models.ExpenseModelAssembler;
import org.test.expensesservice.services.ExpenseService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseModelAssembler assembler;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ExpenseModelAssembler assembler) {
        this.expenseService = expenseService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<Expense> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return assembler.toCollectionModel(expenses);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        System.out.printf("HIII");
        return assembler.toModel(expense);
    }

    @GetMapping("/search/findByCategoryId")
    public CollectionModel<Expense> getExpensesByCategoryId(@RequestParam Long categoryId) {
        List<Expense> expenses = expenseService.getExpenseByCategory(categoryId);
        return assembler.toCollectionModel(expenses);
    }



    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody Expense newExpense) {
        try {
            Expense savedExpense = expenseService.createExpense(newExpense);
            Expense entityModel = assembler.toModel(savedExpense);

        return ResponseEntity
                .created(new URI(entityModel.getRequiredLink("self").getHref()))
                .body(entityModel);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Category not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@RequestBody Expense newExpense, @PathVariable Long id) throws URISyntaxException {
        Expense updatedExpense = expenseService.updateExpense(id,newExpense);
        Expense entityModel = assembler.toModel(updatedExpense);

        return ResponseEntity
                .created(new URI(entityModel.getRequiredLink("self").getHref()))
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
