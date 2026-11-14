package com.example.store.inventory.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;


public record CreateReservationRequest(@NotNull @NotBlank String orderId, @NotNull List<ReservationItem> items) {
}
