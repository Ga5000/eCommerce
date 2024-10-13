package com.api.ga5000.ecommerce.repositories;

import com.api.ga5000.ecommerce.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
