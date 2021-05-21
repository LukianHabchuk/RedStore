package com.example.redstore.controller;

import com.example.redstore.entity.Order;
import com.example.redstore.entity.Product;
import com.example.redstore.entity.User;
import com.example.redstore.service.OrderService;
import com.example.redstore.service.ProductService;
import com.example.redstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public AdminController(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/order/{id}")
    public String removeOrder(@PathVariable("id") long id) {
        orderService.remove(id);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}")
    public String removeUser(@PathVariable("id") long id) {
        userService.remove(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/{id}")
    public String removeProduct(@PathVariable("id") long id) {
        productService.remove(id);
        return "redirect:/admin";
    }

    @PostMapping("/order")
    public String saveOrder(Order order) {
        orderService.create(order);
        return "redirect:/admin";
    }

    @PostMapping("/user")
    public String saveUser(User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @PostMapping("/product")
    public String saveProduct(Product product) {
        productService.create(product);
        return "redirect:/admin";
    }

    @PostMapping("/user/update")
    public String updateUser(User user) {
        userService.update(user);
        return "redirect:/admin";
    }
}
