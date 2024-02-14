package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoSucursalRequest {
    Integer idProducto;
    Integer idSucursal;
    Double precioLocal;
    Integer stock;
}
