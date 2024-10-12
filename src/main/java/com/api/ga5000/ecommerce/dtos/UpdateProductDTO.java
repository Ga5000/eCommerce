package com.api.ga5000.ecommerce.dtos;

import com.api.ga5000.ecommerce.entities.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductDTO(
        @NotBlank String productName,
        @NotBlank String productDescription,
        @NotNull @Min(1) BigDecimal price,
        String imageUrl,
        @NotNull @Min(1) Integer inventory,
        @NotNull Category category
        ) {
}
