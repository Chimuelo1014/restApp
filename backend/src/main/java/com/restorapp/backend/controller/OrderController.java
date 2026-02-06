package com.restorapp.backend.controller;

import com.restorapp.backend.dto.OrderItemRequest;
import com.restorapp.backend.entity.Order;
import com.restorapp.backend.entity.OrderStatus;
import com.restorapp.backend.entity.User;
import com.restorapp.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody List<OrderItemRequest> items) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(user.getId(), items));
  }

  @GetMapping
  public ResponseEntity<List<Order>> getOrders() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if ("ADMIN".equals(user.getRole().name())) {
      return ResponseEntity.ok(orderService.getAllOrders());
    }
    return ResponseEntity.ok(orderService.getOrdersByUser(user.getId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  @PutMapping("/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusMap) {
    OrderStatus status = OrderStatus.valueOf(statusMap.get("status"));
    return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
  }

  @PutMapping("/{id}/cancel")
  public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    orderService.cancelOrder(id, user.getId());
    return ResponseEntity.noContent().build();
  }
}
