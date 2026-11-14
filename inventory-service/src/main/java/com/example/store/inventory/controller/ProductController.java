package com.example.store.inventory.controller;

import com.example.store.inventory.entities.Product;
import com.repositories.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductRepository productRepository;

    ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(){
        return 
    }

}