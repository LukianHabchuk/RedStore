package com.example.redStore.entity;

import com.example.redStore.enums.ProductType;
import com.example.redStore.enums.Tag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product implements Comparable<Product>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double price;
    private String img;
    @Enumerated(value = EnumType.STRING)
    private ProductType type;
    @Enumerated(value = EnumType.STRING)
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
