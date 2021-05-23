package com.example.redstore.entity.sort;

import com.example.redstore.entity.Product;
import com.example.redstore.entity.sort.algorithm.*;
import com.example.redstore.enums.SortAlgorithmName;

import java.util.List;

public class AlgorithmFactory {

    private SortAlgorithm algorithm;

    public AlgorithmFactory(String algorithmName) {
        if (algorithmName.equals(SortAlgorithmName.PRICE_LOW.getName()))
            algorithm = new SortPriceLow();
        if (algorithmName.equals(SortAlgorithmName.PRICE_HIGH.getName()))
            algorithm = new SortPriceHigh();
        if (algorithmName.equals(SortAlgorithmName.RATING.getName()))
            algorithm = new SortByRating();
        if (algorithmName.equals(SortAlgorithmName.NAME.getName()))
            algorithm = new SortByName();
    }

    public List<Product> sort(List<Product> productList) {
        return algorithm.sort(productList);
    }
}
