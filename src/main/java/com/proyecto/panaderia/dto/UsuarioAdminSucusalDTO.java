package com.proyecto.panaderia.dto;

public record UsuarioAdminSucusalDTO(
        Integer id,
        String nombre,
        String dni,
        String usuario,
        String correo,
        String telefono,
        String nombrePerfil,
        Integer idEmpresaAsociada,
        String nombreEmpresaAsociada,
        Integer idSucursalAsociada,
        String nombreSucursalAsociada
) {
}
