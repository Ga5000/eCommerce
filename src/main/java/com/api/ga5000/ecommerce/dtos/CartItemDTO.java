package com.api.ga5000.ecommerce.dtos;

import jakarta.validation.constraints.NotNull;

public record CartItemDTO(@NotNull Long productId, @NotNull Integer quantity) {
}
