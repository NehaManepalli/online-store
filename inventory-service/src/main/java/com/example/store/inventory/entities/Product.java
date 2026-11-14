package com.example.store.inventory.entities;

import java.math.BigDecimal;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "products")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;
}