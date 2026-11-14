package com.example.store.inventory.repositories;

import com.example.store.inventory.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
