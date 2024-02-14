package com.proyecto.panaderia.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {
    private String nombre;
    private String descripcion;
    private Integer idEmpresa;

}
