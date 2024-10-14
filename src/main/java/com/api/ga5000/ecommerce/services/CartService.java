package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.entities.*;
import com.api.ga5000.ecommerce.entities.enums.OrderStatus;
import com.api.ga5000.ecommerce.repositories.*;
import com.api.ga5000.ecommerce.services.interfaces.ICartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       UserRepository userRepository, OrderRepository orderRepository,
                       OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
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
    public Cart removeItemFromCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
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
    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void checkoutCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);
        order.setPhoneNumber(cart.getUser().getPhoneNumber());
        order.setAddress(cart.getUser().getAddresses().get(0));

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            BigDecimal price = product.getProductPrice();
            orderItem.setPrice(price);

            BigDecimal itemTotal = price.multiply(new BigDecimal(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);

            int newInventory = product.getInventory() - cartItem.getQuantity();
            if (newInventory < 0) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
            }
            product.setInventory(newInventory);
            productRepository.save(product);
        }

        order.setTotal(totalAmount);

        orderRepository.save(order);
    }

}
