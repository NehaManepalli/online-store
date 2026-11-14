package com.example.store.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.store.inventory.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{


}