package org.test.expensesservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.test.expensesservice.entities.Expense;
import org.test.expensesservice.services.ExpenseService;

@SpringBootApplication
@EnableFeignClients
public class ExpensesServiceApplication implements org.springframework.boot.CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesServiceApplication.class, args);
    }

    @Autowired
    RepositoryRestConfiguration repositoryRestConfiguration;
    @Autowired
    ExpenseService expenseService;
    @Override
    public void run(String... args) throws Exception {

        Expense expense = new Expense();
        expense.setDescription("Test Expense");
        expense.setAmount(100.0);
        expense.setCategoryId(1L);
        expenseService.createExpense(expense);
    }
}
