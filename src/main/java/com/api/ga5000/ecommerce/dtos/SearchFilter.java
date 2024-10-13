package com.api.ga5000.ecommerce.dtos;

import com.api.ga5000.ecommerce.entities.Category;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record SearchFilter(@Nullable  Category category, @Nullable String name,
                           @Nullable BigDecimal minPrice,
                           @Nullable BigDecimal maxPrice) {
}
