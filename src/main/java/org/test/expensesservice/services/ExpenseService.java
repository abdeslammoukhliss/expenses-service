package org.test.expensesservice.services;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.test.expensesservice.entities.Expense;
import org.test.expensesservice.entities.ExpenseProjection;
import org.test.expensesservice.models.Category;
import org.test.expensesservice.repositories.ExpenseRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryClient categoryClient   ;
    @Autowired
    private ModelMapper modelMapper;
    public List<Expense> getAllExpenses() {

        List<Expense> expenses = expenseRepository.findAll();
        expenses.forEach(expense -> {
            if(expense.getCategory()==null){
                return;
            }
            Category category = categoryClient.getCategoryById(expense.getCategory().getId());
            expense.setCategory(category);
        });
        return expenses;
    }


    public Expense createExpense(Expense expense) {
        Category category = categoryClient.getCategoryById(expense.getCategoryId());
        if(category==null){
            throw new RuntimeException("Category not found");
        }
        return expenseRepository.save(expense)  ;
    }


    public Expense getExpenseById(Long id) {


       Expense expense=  expenseRepository.findById(id).get();
        Category category =categoryClient.getCategoryById(expense.getCategoryId());
        expense.setCategory(category);
        return expense;
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
    public List<Expense> getExpenseByCategory(Long categoryId) {
        return expenseRepository.findExpenseByCategoryId(categoryId);
    }
    public Expense updateExpense(Long id,Expense expense) {
        Expense existingExpense = expenseRepository.findById(id).orElseThrow();
       return  expenseRepository.save(expense);
    }


}
