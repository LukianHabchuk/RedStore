package com.example.redstore.util;

import com.example.redstore.dto.OrderDTO;

public class Validator {

    private Validator() {
    }

    public static boolean isValid(OrderDTO orderDTO) {
        return !orderDTO.getSize().isEmpty() && orderDTO.getCount() > 0;
    }
}
