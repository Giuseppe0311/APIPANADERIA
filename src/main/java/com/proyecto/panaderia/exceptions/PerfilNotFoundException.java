package com.proyecto.panaderia.exceptions;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilNotFoundException extends RuntimeException{
    private String message;
}
