package com.example.store.inventory.repositories;

import com.example.store.inventory.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsByOrderId(Long id);
    List<Reservation> findByOrderId(Long id);
}
