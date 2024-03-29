package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.exceptions.UsuarioNotFoundException;
import com.proyecto.panaderia.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailLoaderImpl implements UserDetailsService {
    // Inyección del repositorio de usuarios.
    private final UsuarioRepositorio usuarioRepositorio;
    // Implementación del método para cargar un usuario por su nombre de usuario.
    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepositorio.findByUsuario(username).orElseThrow(() -> new UsuarioNotFoundException("El usuario no esta registrado"));
    }
}
