package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuarios,Integer> {
    Optional<Usuarios> findByUsuario(String usuario);
}
