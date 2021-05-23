package com.example.redstore.entity.sort.algorithm;

import com.example.redstore.entity.Product;

import java.util.List;

public class SortPriceLow implements SortAlgorithm{
    @Override
    public List<Product> sort(List<Product> productList) {
        productList.sort(Product::compareTo);
        return productList;
    }
}
