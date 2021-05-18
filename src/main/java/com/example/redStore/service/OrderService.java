package com.example.redStore.service;

import com.example.redStore.dto.OrderDTO;
import com.example.redStore.entity.Order;
import com.example.redStore.exception.OrderNotFoundException;
import com.example.redStore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.redStore.util.Validator.isValid;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order) {
        return repository.save(order);
    }

    public Order create(long id, OrderDTO orderDTO, long userId) {
        return isValid(orderDTO) ? new Order(userId, id,
                orderDTO.getSize(), orderDTO.getCount()) : null;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " was not found"));
    }

    public Order getByUserId(Long id) {
        return repository.findByUserId(id).orElseThrow(() -> new OrderNotFoundException("Order with user id: " + id + " was not found"));
    }

    public Order getByProductId(Long id) {
        return repository.findByProductId(id).orElseThrow(() -> new OrderNotFoundException("Order with product id: " + id + " was not found"));
    }
}
