package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuarios,Integer> {
    Optional<Usuarios> findByUsuario(String usuario);
}
