package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepositorio extends JpaRepository<Productos,Integer> {
}
