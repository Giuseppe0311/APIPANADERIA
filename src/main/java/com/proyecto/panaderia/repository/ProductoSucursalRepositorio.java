package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.ProductoSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoSucursalRepositorio extends JpaRepository<ProductoSucursal,Integer> {
    @Query(value = "SELECT * FROM producto_sucursal where sucursales_id=?",nativeQuery = true)
    List<ProductoSucursal> findBySucursalesId(Integer id);
}
