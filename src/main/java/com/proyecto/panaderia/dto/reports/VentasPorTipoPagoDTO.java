package com.proyecto.panaderia.dto.reports;

public interface VentasPorTipoPagoDTO {
    String getTipoPago();
    Double getTotalPorMetodo();
    Double getPorcentajeDelTotal();
}
