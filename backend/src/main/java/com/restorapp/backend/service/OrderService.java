package com.restorapp.backend.service;

import com.restorapp.backend.entity.Order;
import com.restorapp.backend.entity.OrderStatus;
import com.restorapp.backend.entity.User;

import java.util.List;

public interface OrderService {
  Order createOrder(Long userId, List<com.restorapp.backend.dto.OrderItemRequest> items);

  List<Order> getAllOrders();

  List<Order> getOrdersByUser(Long userId);

  Order getOrderById(Long id);

  Order updateOrderStatus(Long id, OrderStatus status);

  void cancelOrder(Long id, Long userId);
}
