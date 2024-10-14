package com.api.ga5000.ecommerce.repositories;

import com.api.ga5000.ecommerce.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
