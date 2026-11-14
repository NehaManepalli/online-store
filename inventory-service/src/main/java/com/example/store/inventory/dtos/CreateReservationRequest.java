package com.example.store.inventory.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;


public record CreateReservationRequest(@NotNull @Positive Long orderId, @NotEmpty @Valid List<ReservationItem> items) {
}
