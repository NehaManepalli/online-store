package com.example.store.inventory.controller;

import com.example.store.inventory.dtos.CreateReservationRequest;
import com.example.store.inventory.dtos.ReservationResult;
import com.example.store.inventory.entities.Reservation;
import com.example.store.inventory.services.ReservationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    ReservationService reservationService;

    ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<List<Reservation>> reserve(@Valid @RequestBody CreateReservationRequest request) {
        ReservationResult result = reservationService.createReservation(request);
        HttpStatus status = result.result() ? HttpStatus.CREATED : HttpStatus.OK;
        return ResponseEntity.status(status).body(result.reservations());
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<List<Reservation>> deleteReservation(@PathVariable @NotNull @Positive Long orderId) {
        reservationService.deleteReservation(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
