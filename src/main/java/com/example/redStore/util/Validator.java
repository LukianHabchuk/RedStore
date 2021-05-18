package com.example.redStore.util;

import com.example.redStore.dto.OrderDTO;

public class Validator {

    public static boolean isValid(OrderDTO orderDTO) {
        return !orderDTO.getSize().isEmpty() && orderDTO.getCount() > 0;
    }
}
