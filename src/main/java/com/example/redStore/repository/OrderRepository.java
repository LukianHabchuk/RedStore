package com.example.redStore.repository;

import com.example.redStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    Optional<Order> findById(Long id);
    Optional<List<Order>> findAllByUserId(Long userId);
    Optional<List<Order>> findAllByProductId(Long productId);
    void deleteById(long id);
}
