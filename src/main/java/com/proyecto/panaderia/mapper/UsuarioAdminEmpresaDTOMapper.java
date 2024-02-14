package com.proyecto.panaderia.mapper;

import com.proyecto.panaderia.dto.UsuarioAdminEmpresaDTO;
import com.proyecto.panaderia.entity.Usuarios;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsuarioAdminEmpresaDTOMapper implements Function<Usuarios, UsuarioAdminEmpresaDTO>{
    @Override
    public UsuarioAdminEmpresaDTO apply(Usuarios user) {
        return new UsuarioAdminEmpresaDTO(
                user.getId(),
                user.getNombre(),
                user.getDni(),
                user.getUsuario(),
                user.getCorreo(),
                user.getTelefono(),
                user.getPerfiles().stream().findFirst().get().getNombre(),
                user.getEmpresa().getId(),
                user.getEmpresa().getNombre()
        );
    }
}
