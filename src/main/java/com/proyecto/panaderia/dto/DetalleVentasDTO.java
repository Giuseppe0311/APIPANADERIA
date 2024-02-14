package com.proyecto.panaderia.dto;

import org.apache.commons.lang3.IntegerRange;

public record DetalleVentasDTO(
        Integer id,
        Integer cantidad,
        Double precioVenta,
        Double subtotal,
        String nombreProducto,
        Integer idProducto
) {
}
