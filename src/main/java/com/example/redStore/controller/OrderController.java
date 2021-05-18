package com.example.redStore.controller;

import com.example.redStore.dto.OrderDTO;
import com.example.redStore.entity.Order;
import com.example.redStore.service.OrderService;
import com.example.redStore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/addtocart/{id}")
    public String addToCart(@PathVariable("id") long id, OrderDTO orderDTO, Principal principal) {
        if (principal != null) {
            orderService.create(new Order(userService.getByEmail(principal.getName()).getId(),
                    id, orderDTO.getSize(), orderDTO.getCount()));
        }
        return "redirect:/product-details/"+id;
    }
}
