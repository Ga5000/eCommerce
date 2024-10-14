package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.entities.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartService {
    Cart getCartByUserId(Long userId);
    Cart addItemToCart(Long userId, Long productId, Integer quantity);
    Cart removeItemFromCart(Long userId, Long productId);
    void clearCart(Long userId);
}
