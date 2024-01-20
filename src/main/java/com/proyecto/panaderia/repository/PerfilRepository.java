package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Perfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfiles,Integer> {
}
