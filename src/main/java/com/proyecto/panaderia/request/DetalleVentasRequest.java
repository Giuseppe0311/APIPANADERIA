package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentasRequest {
    private Integer idproducto;
    private Integer cantidad;
    private Double precio_venta;
    private Double subtotal;
}
