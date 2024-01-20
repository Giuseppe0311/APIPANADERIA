package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Sucursales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalesRepositorio extends JpaRepository<Sucursales,Integer> {
    @Query(value = "SELECT * FROM sucursales WHERE empresa_id= ?",nativeQuery = true)
    List<Sucursales> findByEmpresaId(Integer id);
}
