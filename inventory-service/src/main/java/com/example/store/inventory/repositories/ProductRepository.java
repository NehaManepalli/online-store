package com.example.store.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.store.inventory.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long>{

}