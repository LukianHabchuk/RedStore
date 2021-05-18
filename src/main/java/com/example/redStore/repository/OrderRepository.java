package com.example.redStore.repository;

import com.example.redStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    Optional<Order> findById(Long id);
    Optional<Order> findByUserId(Long userId);
    Optional<Order> findByProductId(Long productId);
}
