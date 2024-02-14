package com.proyecto.panaderia.request;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequest {
    private String usuario;
    private String contrasena;
    private String dni;
    private String correo;
    private String telefono;
    private String nombre;
    private Integer idempresa;
    private Integer idsucursal;
}
