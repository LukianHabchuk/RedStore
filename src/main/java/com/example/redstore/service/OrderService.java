package com.example.redstore.service;

import com.example.redstore.dto.OrderDTO;
import com.example.redstore.entity.Order;
import com.example.redstore.exception.OrderNotFoundException;
import com.example.redstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.redstore.constants.Constants.*;
import static com.example.redstore.util.Validator.isValid;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order) {
        return repository.save(order);
    }

    public Order create(OrderDTO orderDTO, long userId) {
        return isValid(orderDTO) ? repository.save(new Order(userId, orderDTO.getProductId(),
                orderDTO.getSize(), orderDTO.getCount())) : null;
    }

    public void remove(long id) {
        repository.deleteById(id);
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public List<Order> getByUserId(Long id) {
        return repository.findAllByUserId(id).orElseThrow(() ->
                new OrderNotFoundException(String.format(WITH_WAS_NOT_FOUND, ORDER_ATTRIBUTE, "user id", id)));
    }
}
