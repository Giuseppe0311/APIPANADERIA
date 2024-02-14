package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleComprasRequest {
    String producto;
    Integer cantidad;
    Double precio;
    Double subtotal;
}
