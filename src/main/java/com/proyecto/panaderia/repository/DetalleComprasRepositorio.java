package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.DetalleCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleComprasRepositorio extends JpaRepository<DetalleCompras, Integer> {
    @Query(value = "SELECT * FROM  detalle_compras WHERE compra_id=?", nativeQuery = true)
    List<DetalleCompras> getDetalleComprasByIdCompra(Integer idCompra);
}
