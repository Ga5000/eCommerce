package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.dtos.CartItemDTO;
import com.api.ga5000.ecommerce.entities.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartService {
    void createCart(Long userId);
    Cart getCartById(Long cartId);
    void addItemToCart(Long cartId, CartItemDTO cartItemDTO);
    void removeItemFromCart(Long cartId, Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int quantity);
    void clearCart(Long cartId);
    void checkoutCart(Long cartId);
}
