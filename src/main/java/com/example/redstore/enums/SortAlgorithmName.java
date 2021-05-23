package com.example.redstore.enums;

public enum SortAlgorithmName {
    PRICE_LOW("PRICE_LOW"), PRICE_HIGH("PRICE_HIGH"), RATING("RATING"), NAME("NAME");
    private final String name;

    SortAlgorithmName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
