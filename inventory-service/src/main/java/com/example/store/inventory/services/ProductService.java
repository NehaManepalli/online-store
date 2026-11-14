package com.example.store.inventory.services;

import com.example.store.inventory.dtos.CreateProductRequest;
import com.example.store.inventory.entities.Product;
import com.example.store.inventory.entities.Stock;
import com.example.store.inventory.repositories.ProductRepository;
import com.example.store.inventory.repositories.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
