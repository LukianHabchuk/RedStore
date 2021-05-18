package com.example.redStore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order1") //when using table name "order" will be CommandAcceptanceException
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    private long productId;
    private String productSize;
    private int productCount;

    public Order(long userId, long productId, String productSize, int productCount) {
        this.userId = userId;
        this.productId = productId;
        this.productSize = productSize;
        this.productCount = productCount;
    }
}
