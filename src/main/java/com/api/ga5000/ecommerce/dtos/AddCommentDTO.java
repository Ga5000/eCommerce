package com.api.ga5000.ecommerce.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record AddCommentDTO(@NotBlank String content, @Max(5) int rating) {
}
