package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.dtos.CartItemDTO;
import com.api.ga5000.ecommerce.entities.Cart;
import com.api.ga5000.ecommerce.repositories.CartRepository;
import com.api.ga5000.ecommerce.repositories.ProductRepository;
import com.api.ga5000.ecommerce.services.interfaces.ICartService;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createCart(Long userId) {

    }

    @Override
    public Cart getCartById(Long cartId) {
        return null;
    }

    @Override
    public void addItemToCart(Long cartId, CartItemDTO cartItemDTO) {

    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {

    }

    @Override
    public void clearCart(Long cartId) {

    }

    @Override
    public void checkoutCart(Long cartId) {

    }
}
