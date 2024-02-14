package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.DetalleVentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentasRepositorio extends JpaRepository<DetalleVentas,Integer> {
    @Query(value = "SELECT * FROM detalle_ventas where ventas_id=?",nativeQuery = true)
    List<DetalleVentas> findByIdVenta(Integer idVenta);
}
