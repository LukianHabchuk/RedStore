package com.example.redstore.controller;

import com.example.redstore.dto.OrderDTO;
import com.example.redstore.service.OrderService;
import com.example.redstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/createorder")
    public String createOrder(OrderDTO orderDTO, Principal principal) {
            orderService.create(orderDTO, userService.getByEmail(principal.getName()).getId());
        return "redirect:/product-details/"+orderDTO.getProductId();
    }

    @GetMapping("/order/{id}")
    public String removeOrder(@PathVariable("id") long id) {
        orderService.remove(id);
        return "redirect:/cart";
    }
}
