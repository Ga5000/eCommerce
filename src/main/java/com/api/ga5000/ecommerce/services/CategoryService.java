package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.entities.Category;
import com.api.ga5000.ecommerce.repositories.CategoryRepository;
import com.api.ga5000.ecommerce.services.interfaces.ICategoryService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(String categoryName) {
        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository.findByCategoryName(categoryName));
        if (optionalCategory.isPresent()) {
            throw new RuntimeException("Category with name " + categoryName + " already exists");
        }
        Category category = new Category(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
       List<Category> categories =  categoryRepository.findAll();
       categories.sort(Comparator.comparing(Category::getCategoryName));

       return categories;
    }

    @Override
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        categoryRepository.delete(category);
    }
}
