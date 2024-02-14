package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComprasRepositorio extends JpaRepository<Compras,Integer> {
    @Query(value = "SELECT * FROM compras WHERE sucursal_id =? AND estado_pago != 'ANULADO' ORDER BY id DESC;",nativeQuery = true)
    List<Compras> getComprasBySucursal(Integer idSucursal);
}
