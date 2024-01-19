package com.proyecto.panaderia.handler;

import com.proyecto.panaderia.dto.ApiErrorDto;
import com.proyecto.panaderia.exceptions.PerfilNotFoundException;
import com.proyecto.panaderia.exceptions.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultHandlerException extends ResponseEntityExceptionHandler {
    //Manejador de excepciones para UsuarioNotFoundException.
    @ExceptionHandler
    public ResponseEntity<Object> usuarioNotFound(UsuarioNotFoundException ex) {
        // Crea un DTO de error API personalizado con el estado HTTP y el mensaje de la excepción.
        ApiErrorDto apiError = new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getMessage());
        // Devuelve una respuesta de entidad con el estado HTTP 404 (No encontrado) y el DTO del error.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    // Manejador de excepciones para PerfilNotFoundException.
    @ExceptionHandler
    public ResponseEntity<Object> perfilNotFound(PerfilNotFoundException ex) {
        // Crea y configura el DTO de error de la API como en el método anterior.
        ApiErrorDto apiError = new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getMessage());
        // Devuelve una respuesta de entidad con el estado HTTP 404 y el DTO del error.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
