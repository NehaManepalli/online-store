package com.example.store.inventory.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "qty", nullable = false)
    private Integer quantity;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.RESERVED;


}
