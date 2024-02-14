package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<Pagos,Integer> {
    @Query(value = "SELECT p.* FROM pagos p JOIN compras c ON p.compra_id = c.id WHERE c.sucursal_id =?1",nativeQuery = true)
    List<Pagos> getPagosBySucursal(Integer idSucursal);
}
