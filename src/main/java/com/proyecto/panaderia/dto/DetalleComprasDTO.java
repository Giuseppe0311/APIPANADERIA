package com.proyecto.panaderia.dto;

public record DetalleComprasDTO(
        Integer id,
        String nombreProducto,
        Integer cantidad,
        Double precioCompra,
        Double subtotal

) {
}
