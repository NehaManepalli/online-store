package com.example.store.inventory.dtos;

import com.example.store.inventory.entities.Reservation;

import java.util.List;

public record ReservationResult(boolean result, List<Reservation> reservations) {
}
