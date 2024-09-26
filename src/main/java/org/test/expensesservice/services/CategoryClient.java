package org.test.expensesservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.test.expensesservice.models.Category;

import java.util.List;

@FeignClient(name = "category-service", url = "http://localhost:8081")
// Adjust URL as necessary
public interface CategoryClient {
    @GetMapping("/categories/{id}?projection=fullCategory")
     Category getCategoryById(@PathVariable("id") Long id);
    @GetMapping("/categories")
    List<Category> getAllCategories();

    @PostMapping("/categories")
    Category createCategory(Category category);
}
