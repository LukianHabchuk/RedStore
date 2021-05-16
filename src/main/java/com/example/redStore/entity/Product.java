package com.example.redStore.entity;

import com.example.redStore.enums.ProductType;
import com.example.redStore.enums.Tag;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Comparable<Product>{
    @Id
    private long id;
    private String name;
    private double price;
    private String img;
    private ProductType type;
    private Tag tag;
    private int ratio;
    private boolean available;
    private int countAvailable;
    private String details;

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.price, o.getPrice());
    }
}
