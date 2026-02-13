package com.restorapp.backend.service.impl;

import com.restorapp.backend.dto.OrderItemRequest;
import com.restorapp.backend.entity.*;
import com.restorapp.backend.repository.OrderRepository;
import com.restorapp.backend.repository.ProductRepository;
import com.restorapp.backend.repository.UserRepository;
import com.restorapp.backend.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public Order createOrder(Long userId, List<OrderItemRequest> items) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Order order = Order.builder()
        .user(user)
        .status(OrderStatus.PENDING)
        .items(new ArrayList<>())
        .build();

    BigDecimal total = BigDecimal.ZERO;

    for (OrderItemRequest itemRequest : items) {
      Product product = productRepository.findById(itemRequest.getProductId())
          .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemRequest.getProductId()));

      if (!product.getActive()) {
        throw new RuntimeException("El producto " + product.getName() + " no está activo");
      }

      // Validar stock si fuera necesario (omitido por simplicidad inicial)

      OrderItem orderItem = OrderItem.builder()
          .order(order)
          .product(product)
          .quantity(itemRequest.getQuantity())
          .price(product.getPrice()) // Precio histórico
          .build();

      order.getItems().add(orderItem);
      total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
    }

    order.setTotal(total);
    return orderRepository.save(order);
  }

  @Override
  public List<Order> getAllOrders() {
    List<Order> orders = orderRepository.findAll();
    // Initialize lazy-loaded relationships
    orders.forEach(order -> {
      org.hibernate.Hibernate.initialize(order.getUser());
      org.hibernate.Hibernate.initialize(order.getItems());
      order.getItems().forEach(item -> org.hibernate.Hibernate.initialize(item.getProduct()));
    });
    return orders;
  }

  @Override
  public List<Order> getOrdersByUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);
    // Initialize lazy-loaded relationships
    orders.forEach(order -> {
      org.hibernate.Hibernate.initialize(order.getUser());
      org.hibernate.Hibernate.initialize(order.getItems());
      order.getItems().forEach(item -> org.hibernate.Hibernate.initialize(item.getProduct()));
    });
    return orders;
  }

  @Override
  public Order getOrderById(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    // Initialize lazy-loaded relationships
    org.hibernate.Hibernate.initialize(order.getUser());
    org.hibernate.Hibernate.initialize(order.getItems());
    order.getItems().forEach(item -> org.hibernate.Hibernate.initialize(item.getProduct()));
    return order;
  }

  @Override
  public Order updateOrderStatus(Long id, OrderStatus status) {
    Order order = getOrderById(id);
    order.setStatus(status);
    return orderRepository.save(order);
  }

  @Override
  public void cancelOrder(Long id, Long userId) {
    Order order = getOrderById(id);

    if (!order.getUser().getId().equals(userId)) {
      throw new RuntimeException("No tienes permiso para cancelar este pedido");
    }

    if (order.getStatus() != OrderStatus.PENDING) {
      throw new RuntimeException("Solo se pueden cancelar pedidos en estado PENDING");
    }

    order.setStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }
}
