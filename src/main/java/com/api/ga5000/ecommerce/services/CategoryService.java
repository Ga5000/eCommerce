package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.entities.Category;
import com.api.ga5000.ecommerce.repositories.CategoryRepository;
import com.api.ga5000.ecommerce.services.interfaces.ICategoryService;

import java.util.List;

public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(String categoryName) {
        Category category = new Category(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        categoryRepository.delete(category);
    }
}
