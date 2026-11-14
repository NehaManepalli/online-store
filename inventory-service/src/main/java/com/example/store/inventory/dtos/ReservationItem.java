package com.example.store.inventory.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ReservationItem (@NotNull @PositiveOrZero Long productId, @NotNull @Positive Integer quantity){

}