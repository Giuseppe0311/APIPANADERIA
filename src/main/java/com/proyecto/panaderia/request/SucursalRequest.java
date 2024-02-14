package com.proyecto.panaderia.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalRequest {
    private String nombre;
    private String direccion;
    private String telefono;
    private String informacion;
    private Integer idempresa;
}
