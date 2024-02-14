package com.proyecto.panaderia.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedoresRequest {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    private String ruc;
    private Integer idSucursal;
}
