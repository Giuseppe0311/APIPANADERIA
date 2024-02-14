package com.proyecto.panaderia.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaExistException extends RuntimeException{
    private String message;
}