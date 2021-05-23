package com.example.redstore.entity.sort.algorithm;

import com.example.redstore.entity.Product;

import java.util.Collections;
import java.util.List;

public class SortPriceHigh implements SortAlgorithm{
    @Override
    public List<Product> sort(List<Product> productList) {
        productList.sort(Product::compareTo);
        Collections.reverse(productList);
        return productList;
    }
}
