package com.proyecto.panaderia.repository;

import com.proyecto.panaderia.entity.ProductoSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoSucursalRepositorio extends JpaRepository<ProductoSucursal,Integer> {
    @Query(value = "SELECT * FROM producto_sucursal where sucursales_id=? and status=true order by id desc",nativeQuery = true)
    List<ProductoSucursal> findBySucursalesId(Integer id);
    @Query(value = "SELECT * FROM producto_sucursal where sucursales_id=? and productos_id=? and status=true",nativeQuery = true)
    Optional<ProductoSucursal> findBySucursalesIdAndProductosId(Integer idSucursal,Integer idProducto);

    //METODO PARA OBTENER LOS PRODUCTOS DE ACUERDO A LA SUCURSAL Y  CATEGORIA
    @Query(value ="""
            SELECT ps.* FROM producto_sucursal ps
            INNER JOIN productos p ON ps.productos_id = p.id
            INNER JOIN categorias c ON p.categoria_id = c.id
            WHERE c.id = ?1 and sucursales_id=?2 and ps.status=true
            """,nativeQuery = true)
    List<ProductoSucursal> findBySucursalesIdAndCategoriasId(Integer idcategoria,Integer sucursalId);

    @Query(value = "SELECT sucursales_id from producto_sucursal where status=true",nativeQuery = true)
    List<Integer> idsSucursales();
    @Query(value = "SELECT * FROM producto_sucursal WHERE productos_id = ?1 AND sucursales_id = ?2 AND status = true", nativeQuery = true)
    Optional<ProductoSucursal> findByProductoIdAndSucursalId(Integer productoId, Integer sucursalId);
    @Query(value = "SELECT * FROM producto_sucursal WHERE productos_id = ?1 AND sucursales_id = ?2 AND id <> ?3 AND status = true", nativeQuery = true)
    Optional<ProductoSucursal> findByProductoIdAndSucursalIdExcludingId(Integer productoId, Integer sucursalId, Integer id);

}
