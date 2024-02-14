package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.UsuarioAdminEmpresaDTO;
import com.proyecto.panaderia.request.RegistroRequest;
import org.apache.el.stream.Optional;

import java.util.List;

public interface UserServiceforAdminEmpresa {
    List<UsuarioAdminEmpresaDTO> getAllUserOfEmpresas();
    UsuarioAdminEmpresaDTO getUsuarioByid(Integer id);
    void actualizar(RegistroRequest registroRequest, Integer idusuario);
    void eliminar(Integer idusuario);
}
