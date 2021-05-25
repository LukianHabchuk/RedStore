package com.example.redstore.entity;

import com.example.redstore.enums.ProductType;
import com.example.redstore.enums.Tag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
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
}
