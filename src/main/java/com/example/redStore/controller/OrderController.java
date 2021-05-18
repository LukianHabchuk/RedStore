package com.example.redStore.controller;

import com.example.redStore.dto.OrderDTO;
import com.example.redStore.service.OrderService;
import com.example.redStore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/createorder/{id}")
    public String createOrder(@PathVariable("id") long id, OrderDTO orderDTO, Principal principal) {
            orderService.create(id, orderDTO, userService.getByEmail(principal.getName()).getId());
        return "redirect:/product-details/"+id;
    }

    @GetMapping("/order/{id}")
    public String removeOrder(@PathVariable("id") long id) {
        orderService.remove(id);
        return "redirect:/cart";
    }
}
