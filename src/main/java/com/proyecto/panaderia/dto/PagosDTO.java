package com.proyecto.panaderia.dto;

import java.util.Date;

public record PagosDTO(
        Integer id,
        Date fecha,
        String tipoPago,
        Double monto,
        Integer compraId

) {
}
