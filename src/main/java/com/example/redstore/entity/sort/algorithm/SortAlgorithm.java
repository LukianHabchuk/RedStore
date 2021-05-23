package com.example.redstore.entity.sort.algorithm;

import com.example.redstore.entity.Product;

import java.util.List;

public interface SortAlgorithm {
    List<Product> sort(List<Product> productList);
}
