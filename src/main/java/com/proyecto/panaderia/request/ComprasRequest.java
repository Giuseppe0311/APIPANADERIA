package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComprasRequest {
    Integer idproveedor;
    Double totalCompra;
    Integer idsucursal;
    String tipo_comprobante;
    String num_comprobante;
    private String tipo_pago; // CONTADO O CREDITO
    private String estado_pago; // PAGADO O PENDIENTE
    private Date fecha;
    //DATOS DE DETALLE DE COMPRA
    List<DetalleComprasRequest> detalleCompras;

}
