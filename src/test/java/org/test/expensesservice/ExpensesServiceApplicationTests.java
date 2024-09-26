package org.test.expensesservice;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.test.expensesservice.entities.Expense;
import org.test.expensesservice.entities.ExpenseProjection;
import org.test.expensesservice.models.Category;
import org.test.expensesservice.repositories.ExpenseRepository;
import org.test.expensesservice.services.CategoryClient;
import org.test.expensesservice.services.ExpenseService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpensesServiceApplicationTests {
    @Autowired
    ExpenseService expenseService;
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void contextLoads() {
        expenseRepository.deleteAll();
        Category category = new Category();

        category.setName("Utilities");
        categoryClient.createCategory(category);
    }
    @Test
    public void testCreateExpense() throws Exception {
        Expense expense = new Expense();
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        ResultActions resultActions = mockMvc.perform(post("/expenses")
                .contentType("application/json")
                .content("{\"description\":\"Test Expense\",\"amount\":100.0,\"categoryId\":1}"));
        resultActions.andExpect(status().isCreated());
    }
    @Test
    public void testGetAllExpenses() throws Exception {
        List<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense();
        expense.setCategoryId(1L);
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expenses.add(expense);
        expenseService.createExpense(expense);
        ResultActions resultActions = mockMvc.perform(get("/expenses"));
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.expenses", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.expenses[0].description").value("Test Expense"));
    }
    @Test
    public void testGetExpenseById() throws Exception {
        Expense expense = new Expense();

        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expense.setCategoryId(1L);
        expense=expenseRepository.save(expense);

             ResultActions resultActions = mockMvc.perform(get("/expenses/"+expense.getId()));
        resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Expense"));
    }
    @Test
    public void testGetExpenseByCategory() throws Exception {
        List<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense();
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expenses.add(expense);
        expense.setCategoryId(1L);
        expenseService.createExpense(expense);
        ResultActions resultActions = mockMvc.perform(get("/expenses/search/findByCategoryId?categoryId=1"));
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.expenses", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.expenses[0].description").value("Test Expense"));
    }

    @Test
    public void testDeleteExpense() throws Exception {
        Expense expense = new Expense();
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expense.setCategoryId(1L);
        expense=expenseService.createExpense(expense);
        ResultActions resultActions = mockMvc.perform(delete("/expenses/"+expense.getId() ));
        resultActions.andExpect(status().isNoContent());
    }
    @Test
    public void testUpdateExpense() throws Exception {
        Expense expense = new Expense();

        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expense.setCategoryId(1L);
        expense= expenseService.createExpense(expense);
        expense.setDescription("Updated Expense");
        ResultActions resultActions = mockMvc.perform(put("/expenses/"+expense.getId())
                .contentType("application/json")
                .content("{\"description\":\"Updated Expense\",\"amount\":100.0,\"categoryId\":1}"));
        resultActions.andExpect(status().isCreated());
    }
    @Test
    public void testCreateExpenseWithCategoryNotExist() throws Exception {
        Expense expense = new Expense();
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        ResultActions resultActions = mockMvc.perform(post("/expenses")
                .contentType("application/json")
                .content("{\"description\":\"Test Expense\",\"amount\":100.0,\"categoryId\":100}"));
        resultActions.andExpect(status().isBadRequest());

    }

}
