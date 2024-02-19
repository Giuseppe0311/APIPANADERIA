package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.UsuarioAdminSucusalDTO;
import com.proyecto.panaderia.entity.Usuarios;
import com.proyecto.panaderia.exceptions.EmpresaNotFoundException;
import com.proyecto.panaderia.exceptions.SucursalNotFoundException;
import com.proyecto.panaderia.exceptions.UsuarioExistException;
import com.proyecto.panaderia.exceptions.UsuarioNotFoundException;
import com.proyecto.panaderia.mapper.UsuarioAdminSucursalDTOMapper;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.repository.SucursalesRepositorio;
import com.proyecto.panaderia.repository.UsuarioRepositorio;
import com.proyecto.panaderia.request.RegistroRequest;
import com.proyecto.panaderia.services.UserServiceForSucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceForAdminSucursalimpl implements UserServiceForSucursal {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioAdminSucursalDTOMapper usuarioAdminSucursalDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmpresaRepositorio empresaRepositorio;
    private final SucursalesRepositorio sucursalesRepositorio;

    @Override
    public List<UsuarioAdminSucusalDTO> getAllUserOfEmpresaId(Integer idEmpresa) {
        return usuarioRepositorio.findAllUserbyEmpresaId(idEmpresa)
                .stream()
                .map(usuarioAdminSucursalDTOMapper)
                .toList();
    }

    @Override
    public UsuarioAdminSucusalDTO getUsuarioByid(Integer id) {
        return usuarioRepositorio.findById(id)
                .stream()
                .map(usuarioAdminSucursalDTOMapper)
                .findFirst()
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
    }

    @Override
    public void actualizar(RegistroRequest registroRequest, Integer idusuario) {
        Usuarios usuario = usuarioRepositorio.findById(idusuario).orElseThrow(
                () -> new UsuarioNotFoundException("Usuario no encontrado")
        );
        String usuarioNormalizado = registroRequest.getUsuario().strip().toLowerCase();
        String dniNormalizado = registroRequest.getDni().strip().toLowerCase();
        String correoNormalizado = registroRequest.getCorreo().strip().toLowerCase();
        usuarioRepositorio.findByUsuarioOrDniOrCorreoIgnoreCaseAndTrimExcludingId(usuarioNormalizado, dniNormalizado, correoNormalizado, idusuario)
                .ifPresent(usuario1 -> {
                    throw new UsuarioExistException("El usuario, DNI o correo ya se encuentra registrado");
                });
        // Actualiza la contraseña solo si no es nula ni vacía
        if (registroRequest.getContrasena() != null && !registroRequest.getContrasena().isEmpty()) {
            String encodePassword = passwordEncoder.encode(registroRequest.getContrasena());
            usuario.setContrasena(encodePassword);
        }
        usuario.setUsuario(registroRequest.getUsuario());
        usuario.setDni(registroRequest.getDni());
        usuario.setCorreo(registroRequest.getCorreo());
        usuario.setTelefono(registroRequest.getTelefono());
        usuario.setNombre(registroRequest.getNombre());
        usuario.setEmpresa(empresaRepositorio.findById(registroRequest.getIdempresa()).orElseThrow(
                () -> new EmpresaNotFoundException("Empresa no encontrada")
        ));
        usuario.setSucursal(sucursalesRepositorio.findById(registroRequest.getIdsucursal()).orElseThrow(
                () -> new SucursalNotFoundException("Sucursal no encontrada")
        ));
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminar(Integer idusuario) {
        Usuarios usuario = usuarioRepositorio.findById(idusuario).orElseThrow(
                () -> new UsuarioNotFoundException("Usuario no encontrado")
        );
        usuario.setEstado(false);
        usuarioRepositorio.save(usuario);
    }
}
