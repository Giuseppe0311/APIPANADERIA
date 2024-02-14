package com.proyecto.panaderia.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface ProductosMasVendidosDTO {
    String getCategoria();
    String getProducto();
    Long getCantidadVendida();
    Double getTotalVenta();
}
