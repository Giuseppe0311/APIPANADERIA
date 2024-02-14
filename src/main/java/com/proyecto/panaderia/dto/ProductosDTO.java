package com.proyecto.panaderia.dto;

public record   ProductosDTO(
        Integer id,
        String nombre,
        String descripcion,
        String imagen,
        Double precioBase,
        String nombrecategoria,
        Integer idcategoria,
        String nombreUnidadMedida,
        Integer idUnidadMedida,
        Integer stock
) {
}
