package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.dtos.CartItemDTO;
import com.api.ga5000.ecommerce.entities.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartService {
    Cart getCartByUserId(Long userId);
    Cart getCartById(Long cartId);
    Cart addItemToCart(Long userId, Long productId, Integer quantity);
    Cart removeItemFromCart(Long cartId, Long productId);
    void clearCart(Long cartId);
    void checkoutCart(Long cartId);
}
