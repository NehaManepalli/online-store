package com.example.store.inventory.repositories;

import com.example.store.inventory.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {


        @Modifying
        @Query("update Stock s set s.availableQty = s.availableQty - :qty " +
                "where s.productId = :productId and s.availableQty >= :qty")
        int decrementIfAvailable(@Param("productId") Long productId, @Param("qty") int qty);

        @Modifying
        @Query("UPDATE Stock s set s.availableQty = s.availableQty + :qty where s.productId = :productId ")
        int reStockOnDeleteReservation(@Param("productId") Long productId, @Param("qty") int qty);
}
