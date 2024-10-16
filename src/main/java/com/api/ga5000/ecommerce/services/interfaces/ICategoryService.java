package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.entities.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(String categoryName);
    List<Category> getCategories();
    void deleteCategory(String categoryName);
}
