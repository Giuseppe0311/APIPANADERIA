package com.proyecto.panaderia.request;

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
    private String correo;
    private String telefono;
    private String nombre;
    private Set<Integer> perfiles;
}
