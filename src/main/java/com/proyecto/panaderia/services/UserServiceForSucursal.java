package com.proyecto.panaderia.services;

import com.proyecto.panaderia.dto.UsuarioAdminSucusalDTO;
import com.proyecto.panaderia.request.RegistroRequest;

import java.util.List;

public interface UserServiceForSucursal {
    List<UsuarioAdminSucusalDTO> getAllUserOfSucursal();
    UsuarioAdminSucusalDTO getUsuarioByid(Integer id);
    void actualizar(RegistroRequest registroRequest, Integer idusuario);
    void eliminar(Integer idusuario);
}
