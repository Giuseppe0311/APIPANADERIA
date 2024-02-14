package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.dto.reports.ProductosMasVendidosDTO;
import com.proyecto.panaderia.dto.reports.VentasPorTipoPagoDTO;
import com.proyecto.panaderia.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepositorio extends JpaRepository<Ventas,Integer> {
    @Query(value = "SELECT * FROM ventas where sucursal_id =?1 and status='RESERVADO' order by id desc",nativeQuery = true)
    List<Ventas> findAllReservados(Integer idSucursal);

    @Query(value = "SELECT * FROM ventas where sucursal_id =?1 and status='ANULADO' order by id desc",nativeQuery = true)
    List<Ventas> findAllInactive(Integer idSucursal);
    @Query(value = "SELECT * FROM ventas where sucursal_id =?1 and status='PAGADO' order by id desc",nativeQuery = true)
    List<Ventas> findAllIpagado(Integer idSucursal);
    @Query(value = "SELECT " +
            "c.nombre AS categoria, " +
            "p.nombre AS producto, " +
            "SUM(dv.cantidad) AS cantidadVendida, " +
            "SUM(dv.subtotal) AS totalVenta " +
            "FROM ventas v " +
            "INNER JOIN detalle_ventas dv ON v.id = dv.ventas_id " +
            "INNER JOIN productos p ON dv.productos_id = p.id " +
            "INNER JOIN categorias c ON p.categoria_id = c.id " +
            "WHERE v.sucursal_id = :sucursalId " +
            "AND v.status = 'PAGADO' " +
            "GROUP BY p.categoria_id, p.id " +
            "ORDER BY SUM(dv.subtotal) DESC " +
            "LIMIT 5", nativeQuery = true)
    List<ProductosMasVendidosDTO> findTop5ProductosPorSucursal(@Param("sucursalId") Integer sucursalId);


    @Query(value = "SELECT " +
            "v.tipo_pago AS tipoPago, " +
            "SUM(v.total) AS totalPorMetodo, " +
            "SUM(v.total) / (SELECT SUM(total) FROM ventas WHERE sucursal_id = :sucursalId AND status = 'PAGADO') * 100 AS porcentajeDelTotal " +
            "FROM ventas v " +
            "WHERE v.sucursal_id = :sucursalId AND v.status = 'PAGADO' " +
            "GROUP BY v.tipo_pago " +
            "ORDER BY totalPorMetodo DESC", nativeQuery = true)
    List<VentasPorTipoPagoDTO> findVentasPorTipoPago(@Param("sucursalId") Integer sucursalId);
}

