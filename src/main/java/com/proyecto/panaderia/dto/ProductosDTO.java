package com.proyecto.panaderia.dto;

public record ProductosDTO(
        Integer id,
        String nombre,
        String descripcion,
        String imagen,
        Double precioBase,
        Integer stock
) {
}
