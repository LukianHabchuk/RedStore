package com.example.redstore.entity.sort.algorithm;

import com.example.redstore.entity.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByName implements SortAlgorithm {
    @Override
    public List<Product> sort(List<Product> productList) {
        return productList.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }
}
