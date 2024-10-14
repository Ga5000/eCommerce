package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.entities.*;
import com.api.ga5000.ecommerce.repositories.*;
import com.api.ga5000.ecommerce.services.interfaces.ICartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            cartRepository.save(cart);
        }
        return cart;
    }

    @Transactional
    @Override
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getInventory() <= 0){
            throw new RuntimeException("Product is out of stock");
        }

        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        cartItem -> cartItem.setQuantity(cartItem.getQuantity() + quantity),
                        () -> {
                            CartItem item = new CartItem();
                            item.setProduct(product);
                            item.setQuantity(quantity);
                            item.setCart(cart);
                            cart.getCartItems().add(item);
                        }
                );

        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    @Override
    public void clearCart(Long userId) {
       Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }


}
