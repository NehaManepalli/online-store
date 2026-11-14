package com.example.store.inventory.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;


public record CreateReservationRequest(@NotBlank @Positive  Long orderId, @NotEmpty @Valid List<ReservationItem> items) {
}
