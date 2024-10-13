package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.dtos.CartItemDTO;
import com.api.ga5000.ecommerce.entities.*;
import com.api.ga5000.ecommerce.repositories.CartItemRepository;
import com.api.ga5000.ecommerce.repositories.CartRepository;
import com.api.ga5000.ecommerce.repositories.ProductRepository;
import com.api.ga5000.ecommerce.repositories.UserRepository;
import com.api.ga5000.ecommerce.services.interfaces.ICartService;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createCart(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);

        cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public void addItemToCart(Long cartId, CartItemDTO cartItemDTO) {

    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long itemId, Integer quantity) {

    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.getCartItems().clear();
    }

    @Override
    public void checkoutCart(Long cartId) {
        Cart cart = getCartById(cartId);
    }
}
