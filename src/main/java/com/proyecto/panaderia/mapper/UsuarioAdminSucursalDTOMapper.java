package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.UsuarioAdminSucusalDTO;
import com.proyecto.panaderia.entity.Usuarios;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsuarioAdminSucursalDTOMapper implements Function<Usuarios, UsuarioAdminSucusalDTO> {
    @Override
    public UsuarioAdminSucusalDTO apply(Usuarios usuarios) {
        return new UsuarioAdminSucusalDTO(
                usuarios.getId(),
                usuarios.getNombre(),
                usuarios.getDni(),
                usuarios.getUsuario(),
                usuarios.getCorreo(),
                usuarios.getTelefono(),
                usuarios.getPerfiles().stream().findFirst().get().getNombre(),
                usuarios.getEmpresa().getId(),
                usuarios.getEmpresa().getNombre(),
                usuarios.getSucursal().getId(),
                usuarios.getSucursal().getNombre()
        );
    }
}
