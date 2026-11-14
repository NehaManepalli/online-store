package com.example.store.inventory.services;

import com.example.store.inventory.dtos.CreateReservationRequest;
import com.example.store.inventory.dtos.ReservationItem;
import com.example.store.inventory.dtos.ReservationResult;
import com.example.store.inventory.entities.Reservation;
import com.example.store.inventory.exceptions.InsufficientStockException;
import com.example.store.inventory.exceptions.InvalidReservationException;
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

    public ReservationService(ReservationRepository reservationRepository,
                              StockRepository stockRepository) {
        this.reservationRepository = reservationRepository;
        this.stockRepository = stockRepository;
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

        if (reservationRepository.existsByOrderId(request.orderId())) {
            return new ReservationResult(false,
                    reservationRepository.findByOrderId(request.orderId()));
        }

        List<ReservationItem> items = new ArrayList<>(request.items());   // copy, then sort
        items.sort(Comparator.comparingLong(ReservationItem::productId));

        List<Reservation> reservations = new ArrayList<>();
        for (ReservationItem item : items) {
            int updated = stockRepository.decrementIfAvailable(item.productId(), item.quantity());
            if (updated == 0) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + item.productId());
            }
            Reservation reservation = new Reservation();
            reservation.setOrderId(request.orderId());
            reservation.setProductId(item.productId());
            reservation.setQuantity(item.quantity());
            reservations.add(reservation);
        }

        return new ReservationResult(true, reservationRepository.saveAll(reservations));
    }
}
