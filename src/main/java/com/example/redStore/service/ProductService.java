package com.example.redStore.service;

import com.example.redStore.entity.Product;
import com.example.redStore.exception.ProductNotFoundException;
import com.example.redStore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException("product with id: " + id + " was not found"));
    }
}
