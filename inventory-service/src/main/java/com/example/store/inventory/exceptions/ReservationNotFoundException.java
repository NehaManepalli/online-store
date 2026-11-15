package com.example.store.inventory.exceptions;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(String message){
        super(message);
    }
}
