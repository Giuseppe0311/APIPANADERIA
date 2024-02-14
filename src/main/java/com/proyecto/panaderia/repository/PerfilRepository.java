package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Perfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfiles,Integer> {
    @Query(value = "SELECT * FROM perfiles WHERE nombre=?",nativeQuery = true)
    Optional<Perfiles> findByNombre(String nombre);
}
