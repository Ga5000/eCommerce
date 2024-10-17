package com.api.ga5000.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddCommentDTO(@NotBlank String content, @NotNull int rating) {
}
