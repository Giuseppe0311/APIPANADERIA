package com.proyecto.panaderia.dto;

public record ProductoSucursalDTO(
        Integer idProductoSucursal,
        Integer idSucursal,
        String nombreSucursal,
        ProductosDTO productos,
        Double precioLocal,
        Integer stock
) {
}
