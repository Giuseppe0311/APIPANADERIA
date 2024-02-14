package com.proyecto.panaderia.dto;

import org.springframework.http.HttpStatus;

public record JWTResponseDto(
        String token,
        String message,
        HttpStatus status
) {
}
