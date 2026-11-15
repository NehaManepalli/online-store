package com.example.store.inventory.services;

import com.example.store.inventory.dtos.CreateReservationRequest;
import com.example.store.inventory.dtos.ReservationItem;
import com.example.store.inventory.dtos.ReservationResult;
import com.example.store.inventory.entities.Product;
import com.example.store.inventory.entities.Reservation;
import com.example.store.inventory.entities.ReservationStatus;
import com.example.store.inventory.exceptions.*;
import com.example.store.inventory.repositories.ProductRepository;
import com.example.store.inventory.repositories.ReservationRepository;
import com.example.store.inventory.repositories.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              StockRepository stockRepository, ProductRepository productRepository) {
        this.reservationRepository = reservationRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ReservationResult createReservation(CreateReservationRequest request) {
        Set<Long> seen = new HashSet<>();
        for (ReservationItem item : request.items()) {
            if (!seen.add(item.productId())) {
                throw new InvalidReservationException(
                        "Duplicate product id in request: " + item.productId());
            }
        }
        List<Reservation> existingReservations = reservationRepository.findByOrderId(request.orderId());
        if(!existingReservations.isEmpty()){
            boolean anyReleased = existingReservations.stream().anyMatch(reservation -> reservation.getStatus() == ReservationStatus.RELEASED);
            if(anyReleased){
                throw new ConflictException("Reservation is already released : " + request.orderId());
            }
            return new ReservationResult(false,existingReservations);
        }

        List<ReservationItem> items = new ArrayList<>(request.items());   // copy, then sort
        items.sort(Comparator.comparingLong(ReservationItem::productId));

        List<Reservation> reservations = new ArrayList<>();
        for (ReservationItem item : items) {
            int updated = stockRepository.decrementIfAvailable(item.productId(), item.quantity());
            if (updated == 0) {
                Optional<Product> product = productRepository.findById(item.productId());
                if(product.isPresent()){
                    throw new ResourceNotFoundException("Product not found for id : " + item.productId());
                } else {
                    throw new InsufficientStockException(
                            "Insufficient stock for product: " + item.productId());
                }
            }
            Reservation reservation = new Reservation();
            reservation.setOrderId(request.orderId());
            reservation.setProductId(item.productId());
            reservation.setQuantity(item.quantity());
            reservations.add(reservation);
        }

        return new ReservationResult(true, reservationRepository.saveAll(reservations));
    }

    @Transactional
    public void deleteReservation(Long orderId) {
        List<Reservation> reservationList = reservationRepository.findByOrderId(orderId);

        if(reservationList.isEmpty()){
            throw new ResourceNotFoundException("No reservation found for order id : " + orderId);
        }
        reservationList.sort(Comparator.comparingLong(Reservation::getProductId));
        for(Reservation reservation : reservationList) {
            int result = reservationRepository.releaseReservation(reservation.getId(),ReservationStatus.RESERVED,ReservationStatus.RELEASED);
            if(result == 1)
                stockRepository.reStockOnDeleteReservation(reservation.getProductId(),reservation.getQuantity());
        }

    }
}
