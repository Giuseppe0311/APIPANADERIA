package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasRequest {
    private Integer idsucursal;
    private Integer idusuario;
    private String tipo_pago;
    private String tipo_comprobante;
    private String num_comprobante;
    private Double total;

    // Detalle de ventas
    List<DetalleVentasRequest> productos;
}
