package com.example.redstore.controller;

import com.example.redstore.entity.Product;
import com.example.redstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Product getById(long id) {
        return productService.getById(id);
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return productService.getAll();
    }

}
