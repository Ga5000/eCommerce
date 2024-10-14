package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.entities.Order;

public interface IOrderService {
    Order getOrderById(Long orderId);
    Order placeOrder(Long userId, Long addressId);

}
