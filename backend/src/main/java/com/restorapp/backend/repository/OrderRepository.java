package com.restorapp.backend.repository;

import com.restorapp.backend.entity.Order;
import com.restorapp.backend.entity.OrderStatus;
import com.restorapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser(User user);

  List<Order> findByStatus(OrderStatus status);

  List<Order> findByUserOrderByCreatedAtDesc(User user);
}
