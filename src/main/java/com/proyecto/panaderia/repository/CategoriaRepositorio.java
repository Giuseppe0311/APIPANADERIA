package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categorias,Integer> {
}
