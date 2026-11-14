package com.example.store.inventory.dtos;

import java.util.Map;

public record ErrorResponse (String message, Map<String,String> map){
}
