package com.example.redStore.repository;

import com.example.redStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductById(Long id);
}
