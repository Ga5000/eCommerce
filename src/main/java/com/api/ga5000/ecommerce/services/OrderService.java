package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.entities.*;
import com.api.ga5000.ecommerce.entities.enums.OrderStatus;
import com.api.ga5000.ecommerce.repositories.OrderRepository;
import com.api.ga5000.ecommerce.repositories.ProductRepository;
import com.api.ga5000.ecommerce.repositories.UserRepository;
import com.api.ga5000.ecommerce.services.interfaces.IOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order getOrderById(Long orderId) {
       return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    @Override
    public Order placeOrder(Long userId, Long addressId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = user.getAddresses().stream()
                .filter(a -> a.getAddressId().equals(addressId))
                .findFirst().orElseThrow(() -> new RuntimeException("Address not found"));

        Cart cart = user.getCart();
        if(cart.getCartItems().isEmpty()) throw new RuntimeException("Cart is empty");

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.PENDING);
        order.setPhoneNumber(user.getPhoneNumber());

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

        return order;
    }

}
