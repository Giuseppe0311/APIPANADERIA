package com.proyecto.panaderia.dto;

import java.util.Date;

public record VentasDTO(
        Integer id,
        Integer idUsuario,
        String tipoPago,
        String tipoComprobante,
        String numComprobante,
        Double total,
        Date fecha,
        String nombreUsuario,
        String correo,
        String telefono,
        String nombreSucursal,
        String estado
) {
}
