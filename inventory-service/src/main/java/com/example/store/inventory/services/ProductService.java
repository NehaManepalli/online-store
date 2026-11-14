package com.example.store.inventory.services;

import com.example.store.inventory.CreateProductRequest;
import com.example.store.inventory.entities.Product;
import com.example.store.inventory.entities.Stock;
import com.example.store.inventory.repositories.ProductRepository;
import com.example.store.inventory.repositories.StockRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;


    @Transactional
    public Product createProduct(CreateProductRequest productRequest){
            Product product = new Product();
            product.setName(productRequest.name());
            product.setPrice(productRequest.price());

            product = productRepository.save(product);
            Stock stock = new Stock();
            stock.setProductId(product.getId());
            stock.setAvailableQty(productRequest.initialQuantity());
            stockRepository.save(stock);

            return product;
    }
}
